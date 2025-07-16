import { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Routes, Route } from 'react-router-dom'
import { Row, Col, Card } from 'react-bootstrap'
import { fetchDashboardData } from '../../store/slices/dashboardSlice'
import { fetchAppointments } from '../../store/slices/appointmentsSlice'

const PatientDashboard = () => {
  const dispatch = useDispatch()
  const { data: dashboardData } = useSelector((state) => state.dashboard)
  const { appointments } = useSelector((state) => state.appointments)

  useEffect(() => {
    dispatch(fetchDashboardData('patient'))
    dispatch(fetchAppointments({ role: 'patient' }))
  }, [dispatch])

  const DashboardHome = () => (
    <div>
      <h2 className="mb-4">Patient Dashboard</h2>
      <Row>
        <Col xs={12} sm={4}>
          <Card className="text-center mb-3">
            <Card.Body>
              <Card.Title className="text-primary">
                {appointments?.length || 0}
              </Card.Title>
              <Card.Text>My Appointments</Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col xs={12} sm={4}>
          <Card className="text-center mb-3">
            <Card.Body>
              <Card.Title className="text-success">
                {appointments?.filter(apt => apt.status === 'completed')?.length || 0}
              </Card.Title>
              <Card.Text>Completed</Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col xs={12} sm={4}>
          <Card className="text-center mb-3">
            <Card.Body>
              <Card.Title className="text-warning">
                {appointments?.filter(apt => apt.status === 'scheduled')?.length || 0}
              </Card.Title>
              <Card.Text>Upcoming</Card.Text>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <Row>
        <Col xs={12} lg={8} className="mb-3">
          <Card>
            <Card.Header>
              <h5>My Appointments</h5>
            </Card.Header>
            <Card.Body>
              {appointments?.length > 0 ? (
                <div className="table-responsive">
                  <table className="table table-striped">
                    <thead>
                      <tr>
                        <th>Date</th>
                        <th>Doctor</th>
                        <th>Status</th>
                        <th>Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      {appointments.slice(0, 5).map((appointment, index) => (
                        <tr key={index}>
                          <td>{appointment.date || 'TBD'}</td>
                          <td>Dr. {appointment.doctorName || 'Doctor Name'}</td>
                          <td>
                            <span className={`badge bg-${
                              appointment.status === 'completed' ? 'success' :
                              appointment.status === 'scheduled' ? 'primary' :
                              appointment.status === 'pending' ? 'warning' : 'secondary'
                            }`}>
                              {appointment.status || 'pending'}
                            </span>
                          </td>
                          <td>
                            <button className="btn btn-sm btn-outline-primary me-2">
                              View
                            </button>
                            <button className="btn btn-sm btn-outline-info">
                              Bill
                            </button>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              ) : (
                <p className="text-muted">No appointments found</p>
              )}
            </Card.Body>
          </Card>
        </Col>
        <Col xs={12} lg={4} className="mb-3">
          <Card>
            <Card.Header>
              <h5>Quick Actions</h5>
            </Card.Header>
            <Card.Body>
              <div className="d-grid gap-2">
                <button className="btn btn-primary">Book Appointment</button>
                <button className="btn btn-outline-secondary">View Medical Records</button>
                <button className="btn btn-outline-info">View Bills</button>
                <button className="btn btn-outline-success">Update Profile</button>
              </div>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </div>
  )

  return (
    <Routes>
      <Route path="/" element={<DashboardHome />} />
    </Routes>
  )
}

export default PatientDashboard
