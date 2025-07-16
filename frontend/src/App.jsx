import { Routes, Route, Navigate } from 'react-router-dom'
import { useSelector } from 'react-redux'
import { Container } from 'react-bootstrap'
import Navbar from './components/common/Navbar'
import Login from './pages/Login'
import Register from './pages/Register'
import AdminDashboard from './pages/admin/AdminDashboard'
import DoctorDashboard from './pages/doctor/DoctorDashboard'
import PatientDashboard from './pages/patient/PatientDashboard'
import ReceptionistDashboard from './pages/receptionist/ReceptionistDashboard'
import PrivateRoute from './components/common/PrivateRoute'
import './App.css'

function App() {
  const { isAuthenticated, user } = useSelector((state) => state.auth)

  return (
    <div className="App">
      {isAuthenticated && <Navbar />}
      <Container fluid className={isAuthenticated ? 'py-3 px-3' : 'p-0'}>
        <Routes>
          {/* Public Routes */}
          <Route 
            path="/login" 
            element={!isAuthenticated ? <Login /> : <Navigate to={`/${user?.role}`} />} 
          />
          <Route 
            path="/register" 
            element={!isAuthenticated ? <Register /> : <Navigate to={`/${user?.role}`} />} 
          />
          
          {/* Protected Routes */}
          <Route path="/admin/*" element={
            <PrivateRoute allowedRoles={['admin']}>
              <AdminDashboard />
            </PrivateRoute>
          } />
          
          <Route path="/doctor/*" element={
            <PrivateRoute allowedRoles={['doctor']}>
              <DoctorDashboard />
            </PrivateRoute>
          } />
          
          <Route path="/patient/*" element={
            <PrivateRoute allowedRoles={['patient']}>
              <PatientDashboard />
            </PrivateRoute>
          } />
          
          <Route path="/receptionist/*" element={
            <PrivateRoute allowedRoles={['receptionist']}>
              <ReceptionistDashboard />
            </PrivateRoute>
          } />
          
          {/* Default Route */}
          <Route 
            path="/" 
            element={
              isAuthenticated ? 
                <Navigate to={`/${user?.role}`} /> : 
                <Navigate to="/login" />
            } 
          />
        </Routes>
      </Container>
    </div>
  )
}

export default App
