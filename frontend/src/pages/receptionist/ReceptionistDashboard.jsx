import { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Routes, Route } from 'react-router-dom'
import { Row, Col, Card } from 'react-bootstrap'
import { fetchDashboardData } from '../../store/slices/dashboardSlice'
import { fetchAppointments } from '../../store/slices/appointmentsSlice'

const ReceptionistDashboard = () => {
  const dispatch = useDispatch()
  const { data: dashboardData } = useSelector((state) => state.dashboard)
  const { appointments } = useSelector((state) => state.appointments)

  useEffect(() => {
    dispatch(fetchDashboardData('receptionist'))
    dispatch(fetchAppointments({ role: 'receptionist' }))
  }, [dispatch])

  const DashboardHome = () => (
    <div>
      <h2 className="mb-4">Receptionist Dashboard</h2>
      <Row>
        <Col md={3}>
          <Card className="text-center mb-3">
            <Card.Body>
              <Card.Title className="text-primary">
                {appointments?.length || 0}
              </Card.Title>
              <Card.Text>Total Appointments</Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col md={3}>
          <Card className="text-center mb-3">
            <Card.Body>
              <Card.Title className="text-warning">
                {appointments?.filter(apt => apt.status === 'pending')?.length || 0}
              </Card.Title>
              <Card.Text>Pending</Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col md={3}>
          <Card className="text-center mb-3">
            <Card.Body>
              <Card.Title className="text-success">
                {appointments?.filter(apt => apt.status === 'scheduled')?.length || 0}
              </Card.Title>
              <Card.Text>Scheduled</Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col md={3}>
          <Card className="text-center mb-3">
            <Card.Body>
              <Card.Title className="text-info">
                {appointments?.filter(apt => apt.status === 'completed')?.length || 0}
              </Card.Title>
              <Card.Text>Completed</Card.Text>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <Row>
        <Col md={8}>
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
                        <th>Doctor</th>
                        <th>Status</th>
                        <th>Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      {appointments.slice(0, 5).map((appointment, index) => (
                        <tr key={index}>
                          <td>{appointment.time || '10:00 AM'}</td>
                          <td>{appointment.patientName || 'Patient Name'}</td>
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
                              Edit
                            </button>
                            <button className="btn btn-sm btn-outline-success">
                              Check-in
                            </button>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              ) : (
                <p className="text-muted">No appointments for today</p>
              )}
            </Card.Body>
          </Card>
        </Col>
        <Col md={4}>
          <Card>
            <Card.Header>
              <h5>Quick Actions</h5>
            </Card.Header>
            <Card.Body>
              <div className="d-grid gap-2">
                <button className="btn btn-primary">New Appointment</button>
                <button className="btn btn-outline-secondary">Patient Check-in</button>
                <button className="btn btn-outline-info">View Schedule</button>
                <button className="btn btn-outline-success">Patient Registration</button>
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

export default ReceptionistDashboard
