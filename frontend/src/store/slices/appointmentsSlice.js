import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

// Get token from localStorage for API calls
const getAuthConfig = () => {
  const token = localStorage.getItem('token');
  return {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
};

// Async thunks for appointments
export const fetchAppointments = createAsyncThunk(
  'appointments/fetchAppointments',
  async ({ role, status }, { rejectWithValue }) => {
    try {
      let url = `${API_BASE_URL}/${role}/appointments`;
      if (status) {
        url += `?status=${status}`;
      }
      
      const response = await axios.get(url, getAuthConfig());
      return response.data;
    } catch (error) {
      return rejectWithValue(
        error.response?.data?.message || 'Failed to fetch appointments'
      );
    }
  }
);

export const createAppointment = createAsyncThunk(
  'appointments/createAppointment',
  async ({ appointmentData, role }, { rejectWithValue }) => {
    try {
      const url = `${API_BASE_URL}/${role}/appointments`;
      const response = await axios.post(url, appointmentData, getAuthConfig());
      return response.data;
    } catch (error) {
      return rejectWithValue(
        error.response?.data?.message || 'Failed to create appointment'
      );
    }
  }
);

export const updateAppointmentStatus = createAsyncThunk(
  'appointments/updateAppointmentStatus',
  async ({ appointmentId, status, role }, { rejectWithValue }) => {
    try {
      const url = `${API_BASE_URL}/${role}/appointments/${appointmentId}/status`;
      const response = await axios.put(url, { status }, getAuthConfig());
      return response.data;
    } catch (error) {
      return rejectWithValue(
        error.response?.data?.message || 'Failed to update appointment status'
      );
    }
  }
);

export const addDoctorNote = createAsyncThunk(
  'appointments/addDoctorNote',
  async ({ appointmentId, diagnosis, instructions, medications }, { rejectWithValue }) => {
    try {
      const url = `${API_BASE_URL}/doctor/appointments/${appointmentId}/note`;
      const noteData = {
        appointmentId,
        diagnosis,
        instructions,
        medications: medications || []
      };
      const response = await axios.post(url, noteData, getAuthConfig());
      return response.data;
    } catch (error) {
      return rejectWithValue(
        error.response?.data?.message || 'Failed to add doctor note'
      );
    }
  }
);

export const fetchDoctorNote = createAsyncThunk(
  'appointments/fetchDoctorNote',
  async (appointmentId, { rejectWithValue }) => {
    try {
      const url = `${API_BASE_URL}/doctor/appointments/${appointmentId}/note`;
      const response = await axios.get(url, getAuthConfig());
      return { appointmentId, note: response.data };
    } catch (error) {
      return rejectWithValue(
        error.response?.data?.message || 'Failed to fetch doctor note'
      );
    }
  }
);

const appointmentsSlice = createSlice({
  name: 'appointments',
  initialState: {
    appointments: [],
    currentAppointment: null,
    doctorNotes: {},
    loading: false,
    error: null,
  },
  reducers: {
    clearError: (state) => {
      state.error = null;
    },
    setCurrentAppointment: (state, action) => {
      state.currentAppointment = action.payload;
    },
    clearAppointments: (state) => {
      state.appointments = [];
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch appointments
      .addCase(fetchAppointments.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchAppointments.fulfilled, (state, action) => {
        state.loading = false;
        state.appointments = action.payload;
        state.error = null;
      })
      .addCase(fetchAppointments.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      // Create appointment
      .addCase(createAppointment.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(createAppointment.fulfilled, (state, action) => {
        state.loading = false;
        state.appointments.push(action.payload);
        state.error = null;
      })
      .addCase(createAppointment.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      // Update appointment status
      .addCase(updateAppointmentStatus.fulfilled, (state, action) => {
        const updatedAppointment = action.payload;
        const index = state.appointments.findIndex(
          (apt) => apt.id === updatedAppointment.id
        );
        if (index !== -1) {
          state.appointments[index] = updatedAppointment;
        }
      })
      // Add doctor note
      .addCase(addDoctorNote.fulfilled, (state, action) => {
        const { appointmentId, note } = action.payload;
        state.doctorNotes[appointmentId] = note;
      })
      // Fetch doctor note
      .addCase(fetchDoctorNote.fulfilled, (state, action) => {
        const { appointmentId, note } = action.payload;
        state.doctorNotes[appointmentId] = note;
      });
  },
});

export const { clearError, setCurrentAppointment, clearAppointments } = appointmentsSlice.actions;
export default appointmentsSlice.reducer;
