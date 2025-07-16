import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

// Test if backend is running
export const testBackendConnection = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/admin/test`, {
      timeout: 5000
    });
    return { success: true, data: response.data };
  } catch (error) {
    return { 
      success: false, 
      error: error.message,
      isConnected: false 
    };
  }
};

// Test login with sample credentials
export const testLogin = async (username = 'admin', password = 'password') => {
  try {
    const response = await axios.post(`${API_BASE_URL}/auth/login`, {
      username,
      password
    });
    return { success: true, data: response.data };
  } catch (error) {
    return { 
      success: false, 
      error: error.response?.data?.message || error.message 
    };
  }
};

// Get API info
export const getAPIInfo = () => {
  return {
    baseURL: API_BASE_URL,
    endpoints: {
      login: `${API_BASE_URL}/auth/login`,
      adminTest: `${API_BASE_URL}/admin/test`,
      doctorTest: `${API_BASE_URL}/doctor/test`,
      patientTest: `${API_BASE_URL}/patient/test`,
      receptionistTest: `${API_BASE_URL}/receptionist/test`,
    }
  };
};
