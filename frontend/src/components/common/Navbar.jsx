import { useSelector, useDispatch } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import { Navbar as BootstrapNavbar, Nav, NavDropdown, Container } from 'react-bootstrap'
import { logoutUser } from '../../store/slices/authSlice'

const Navbar = () => {
  const { user } = useSelector((state) => state.auth)
  const dispatch = useDispatch()
  const navigate = useNavigate()

  const handleLogout = () => {
    dispatch(logoutUser())
    navigate('/login')
  }

  const getRoleDisplayName = (role) => {
    switch (role) {
      case 'admin': return 'Administrator'
      case 'doctor': return 'Doctor'
      case 'patient': return 'Patient'
      case 'receptionist': return 'Receptionist'
      default: return role
    }
  }

  const getNavItems = () => {
    switch (user?.role) {
      case 'admin':
        return (
          <>
            <Nav.Link href="/admin">Dashboard</Nav.Link>
            <Nav.Link href="/admin/users">Users</Nav.Link>
            <Nav.Link href="/admin/appointments">Appointments</Nav.Link>
            <Nav.Link href="/admin/patients">Patients</Nav.Link>
            <Nav.Link href="/admin/doctors">Doctors</Nav.Link>
          </>
        )
      case 'doctor':
        return (
          <>
            <Nav.Link href="/doctor">Dashboard</Nav.Link>
            <Nav.Link href="/doctor/appointments">My Appointments</Nav.Link>
            <Nav.Link href="/doctor/profile">Profile</Nav.Link>
          </>
        )
      case 'patient':
        return (
          <>
            <Nav.Link href="/patient">Dashboard</Nav.Link>
            <Nav.Link href="/patient/appointments">My Appointments</Nav.Link>
            <Nav.Link href="/patient/billing">Billing</Nav.Link>
            <Nav.Link href="/patient/profile">Profile</Nav.Link>
          </>
        )
      case 'receptionist':
        return (
          <>
            <Nav.Link href="/receptionist">Dashboard</Nav.Link>
            <Nav.Link href="/receptionist/appointments">Appointments</Nav.Link>
            <Nav.Link href="/receptionist/schedule">Schedule</Nav.Link>
          </>
        )
      default:
        return null
    }
  }

  return (
    <BootstrapNavbar bg="primary" variant="dark" expand="lg">
      <Container>
        <BootstrapNavbar.Brand href={`/${user?.role}`}>
          Hospital Management System
        </BootstrapNavbar.Brand>
        <BootstrapNavbar.Toggle aria-controls="basic-navbar-nav" />
        <BootstrapNavbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            {getNavItems()}
          </Nav>
          <Nav>
            <NavDropdown 
              title={`${getRoleDisplayName(user?.role)} - ${user?.name || user?.email}`} 
              id="basic-nav-dropdown"
            >
              <NavDropdown.Item href={`/${user?.role}/profile`}>
                Profile
              </NavDropdown.Item>
              <NavDropdown.Divider />
              <NavDropdown.Item onClick={handleLogout}>
                Logout
              </NavDropdown.Item>
            </NavDropdown>
          </Nav>
        </BootstrapNavbar.Collapse>
      </Container>
    </BootstrapNavbar>
  )
}

export default Navbar
