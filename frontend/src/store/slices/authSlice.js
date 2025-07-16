import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

// Async thunks for authentication
export const loginUser = createAsyncThunk(
  'auth/loginUser',
  async ({ username, password }, { rejectWithValue }) => {
    try {
      const response = await axios.post(`${API_BASE_URL}/auth/login`, {
        username,
        password,
      });
      
      const { token } = response.data;
      
      if (token) {
        // Store token in localStorage
        localStorage.setItem('token', token);
        
        // Get user info by testing different endpoints to determine role
        let userInfo = null;
        const config = {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        };
        
        // Try to determine user role by testing endpoints
        try {
          const adminTest = await axios.get(`${API_BASE_URL}/admin/test`, config);
          userInfo = { ...adminTest.data, role: 'admin' };
        } catch {
          try {
            const doctorTest = await axios.get(`${API_BASE_URL}/doctor/test`, config);
            userInfo = { ...doctorTest.data, role: 'doctor' };
          } catch {
            try {
              const patientTest = await axios.get(`${API_BASE_URL}/patient/test`, config);
              userInfo = { ...patientTest.data, role: 'patient' };
            } catch {
              try {
                const receptionistTest = await axios.get(`${API_BASE_URL}/receptionist/test`, config);
                userInfo = { ...receptionistTest.data, role: 'receptionist' };
              } catch {
                throw new Error('Unable to determine user role');
              }
            }
          }
        }
        
        localStorage.setItem('user', JSON.stringify(userInfo));
        
        return {
          user: userInfo,
          token: token
        };
      } else {
        throw new Error('No token received');
      }
    } catch (error) {
      return rejectWithValue(
        error.response?.data?.message || error.message || 'Login failed'
      );
    }
  }
);

export const registerUser = createAsyncThunk(
  'auth/registerUser',
  async (userData, { rejectWithValue }) => {
    try {
      const { role, email, phone, address, department, ...restData } = userData;
      
      let endpoint = '';
      let requestData = {};
      
      if (role === 'patient') {
        endpoint = `${API_BASE_URL}/patient/register`;
        requestData = {
          username: email, // Using email as username
          password: restData.password,
          name: restData.name,
          address: address,
          age: restData.age || 25, // Default age if not provided
          gender: restData.gender || 'Other', // Default gender if not provided
          contact: phone
        };
      } else if (role === 'doctor') {
        endpoint = `${API_BASE_URL}/doctor/register`;
        requestData = {
          username: email, // Using email as username
          password: restData.password,
          name: restData.name,
          specialization: restData.specialization,
          contact: phone,
          email: email
        };
      } else {
        throw new Error('Invalid role selected');
      }
      
      const response = await axios.post(endpoint, requestData);
      return response.data;
    } catch (error) {
      return rejectWithValue(
        error.response?.data?.message || 'Registration failed'
      );
    }
  }
);

export const logoutUser = createAsyncThunk('auth/logoutUser', async () => {
  localStorage.removeItem('token');
  localStorage.removeItem('user');
  return null;
});

// Get initial state from localStorage
const getInitialState = () => {
  const token = localStorage.getItem('token');
  const user = localStorage.getItem('user');
  
  return {
    user: user ? JSON.parse(user) : null,
    token: token || null,
    isAuthenticated: !!token,
    loading: false,
    error: null,
  };
};

const authSlice = createSlice({
  name: 'auth',
  initialState: getInitialState(),
  reducers: {
    clearError: (state) => {
      state.error = null;
    },
    setCredentials: (state, action) => {
      const { user, token } = action.payload;
      state.user = user;
      state.token = token;
      state.isAuthenticated = true;
    },
  },
  extraReducers: (builder) => {
    builder
      // Login cases
      .addCase(loginUser.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(loginUser.fulfilled, (state, action) => {
        state.loading = false;
        state.user = action.payload.user;
        state.token = action.payload.token;
        state.isAuthenticated = true;
        state.error = null;
      })
      .addCase(loginUser.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
        state.isAuthenticated = false;
      })
      // Register cases
      .addCase(registerUser.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(registerUser.fulfilled, (state) => {
        state.loading = false;
        state.error = null;
      })
      .addCase(registerUser.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      // Logout cases
      .addCase(logoutUser.fulfilled, (state) => {
        state.user = null;
        state.token = null;
        state.isAuthenticated = false;
        state.loading = false;
        state.error = null;
      });
  },
});

export const { clearError, setCredentials } = authSlice.actions;
export default authSlice.reducer;
