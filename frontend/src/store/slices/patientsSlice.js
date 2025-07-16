import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

const getAuthConfig = () => {
  const token = localStorage.getItem('token');
  return {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
};

export const fetchPatients = createAsyncThunk(
  'patients/fetchPatients',
  async (_, { rejectWithValue }) => {
    try {
      const response = await axios.get(`${API_BASE_URL}/admin/patients`, getAuthConfig());
      return response.data;
    } catch (error) {
      return rejectWithValue(
        error.response?.data?.message || 'Failed to fetch patients'
      );
    }
  }
);

export const fetchPatientBilling = createAsyncThunk(
  'patients/fetchPatientBilling',
  async (appointmentId, { rejectWithValue }) => {
    try {
      const response = await axios.get(
        `${API_BASE_URL}/patient/appointment/${appointmentId}/billinfo`,
        getAuthConfig()
      );
      return response.data;
    } catch (error) {
      return rejectWithValue(
        error.response?.data?.message || 'Failed to fetch billing info'
      );
    }
  }
);

const patientsSlice = createSlice({
  name: 'patients',
  initialState: {
    patients: [],
    billingInfo: {},
    loading: false,
    error: null,
  },
  reducers: {
    clearError: (state) => {
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchPatients.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchPatients.fulfilled, (state, action) => {
        state.loading = false;
        state.patients = action.payload;
        state.error = null;
      })
      .addCase(fetchPatients.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      .addCase(fetchPatientBilling.fulfilled, (state, action) => {
        const { appointmentId, ...billingData } = action.payload;
        state.billingInfo[appointmentId] = billingData;
      });
  },
});

export const { clearError } = patientsSlice.actions;
export default patientsSlice.reducer;
