import { configureStore } from '@reduxjs/toolkit';
import authSlice from './slices/authSlice';
import usersSlice from './slices/usersSlice';
import appointmentsSlice from './slices/appointmentsSlice';
import patientsSlice from './slices/patientsSlice';
import doctorsSlice from './slices/doctorsSlice';
import dashboardSlice from './slices/dashboardSlice';

export const store = configureStore({
  reducer: {
    auth: authSlice,
    users: usersSlice,
    appointments: appointmentsSlice,
    patients: patientsSlice,
    doctors: doctorsSlice,
    dashboard: dashboardSlice,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        ignoredActions: ['persist/PERSIST'],
      },
    }),
});

export default store;
