import { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Routes, Route } from 'react-router-dom'
import { Row, Col, Card } from 'react-bootstrap'
import { fetchDashboardData } from '../../store/slices/dashboardSlice'
import { fetchAppointments } from '../../store/slices/appointmentsSlice'

const DoctorDashboard = () => {
  const dispatch = useDispatch()
  const { data: dashboardData } = useSelector((state) => state.dashboard)
  const { appointments } = useSelector((state) => state.appointments)

  useEffect(() => {
    dispatch(fetchDashboardData('doctor'))
    dispatch(fetchAppointments({ role: 'doctor' }))
  }, [dispatch])

  const DashboardHome = () => (
    <div>
      <h2 className="mb-4">Doctor Dashboard</h2>
      <Row>
        <Col xs={12} sm={6} md={3}>
          <Card className="text-center mb-3">
            <Card.Body>
              <Card.Title className="text-primary">
                {appointments?.length || 0}
              </Card.Title>
              <Card.Text>Total Appointments</Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col xs={12} sm={6} md={3}>
          <Card className="text-center mb-3">
            <Card.Body>
              <Card.Title className="text-success">
                {appointments?.filter(apt => apt.status === 'completed')?.length || 0}
              </Card.Title>
              <Card.Text>Completed</Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col xs={12} sm={6} md={3}>
          <Card className="text-center mb-3">
            <Card.Body>
              <Card.Title className="text-warning">
                {appointments?.filter(apt => apt.status === 'scheduled')?.length || 0}
              </Card.Title>
              <Card.Text>Scheduled</Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col xs={12} sm={6} md={3}>
          <Card className="text-center mb-3">
            <Card.Body>
              <Card.Title className="text-info">
                {appointments?.filter(apt => apt.status === 'pending')?.length || 0}
              </Card.Title>
              <Card.Text>Pending</Card.Text>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <Row>
        <Col md={12}>
          <Card>
            <Card.Header>
              <h5>Today's Appointments</h5>
            </Card.Header>
            <Card.Body>
              {appointments?.length > 0 ? (
                <div className="table-responsive">
                  <table className="table table-striped">
                    <thead>
                      <tr>
                        <th>Time</th>
                        <th>Patient</th>
                        <th>Status</th>
                        <th>Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      {appointments.slice(0, 5).map((appointment, index) => (
                        <tr key={index}>
                          <td>{appointment.time || '10:00 AM'}</td>
                          <td>{appointment.patientName || 'Patient Name'}</td>
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
                            <button className="btn btn-sm btn-outline-success">
                              Notes
                            </button>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              ) : (
                <p className="text-muted">No appointments scheduled for today</p>
              )}
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

export default DoctorDashboard
