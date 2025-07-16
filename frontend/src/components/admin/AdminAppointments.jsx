import { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Card, Table, Button, Badge } from 'react-bootstrap'
import { fetchAppointments } from '../../store/slices/appointmentsSlice'

const AdminAppointments = () => {
  const dispatch = useDispatch()
  const { appointments, loading } = useSelector((state) => state.appointments)

  useEffect(() => {
    dispatch(fetchAppointments({ role: 'admin' }))
  }, [dispatch])

  const getStatusBadgeColor = (status) => {
    switch (status) {
      case 'completed': return 'success'
      case 'scheduled': return 'primary'
      case 'pending': return 'warning'
      case 'cancelled': return 'danger'
      default: return 'secondary'
    }
  }

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h3>Appointment Management</h3>
        <Button variant="primary">
          New Appointment
        </Button>
      </div>

      <Card>
        <Card.Header>
          <h5>All Appointments</h5>
        </Card.Header>
        <Card.Body>
          {loading ? (
            <div className="text-center">Loading...</div>
          ) : (
            <Table striped bordered hover responsive>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Patient</th>
                  <th>Doctor</th>
                  <th>Date</th>
                  <th>Time</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {appointments?.length > 0 ? (
                  appointments.map((appointment, index) => (
                    <tr key={appointment.id || index}>
                      <td>{appointment.id || index + 1}</td>
                      <td>{appointment.patientName || 'Patient Name'}</td>
                      <td>Dr. {appointment.doctorName || 'Doctor Name'}</td>
                      <td>{appointment.date || '2024-01-15'}</td>
                      <td>{appointment.time || '10:00 AM'}</td>
                      <td>
                        <Badge bg={getStatusBadgeColor(appointment.status)}>
                          {appointment.status || 'pending'}
                        </Badge>
                      </td>
                      <td>
                        <div className="btn-group btn-group-sm">
                          <Button variant="outline-primary" size="sm">
                            View
                          </Button>
                          <Button variant="outline-success" size="sm">
                            Edit
                          </Button>
                          <Button variant="outline-danger" size="sm">
                            Cancel
                          </Button>
                        </div>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="7" className="text-center">
                      No appointments found
                    </td>
                  </tr>
                )}
              </tbody>
            </Table>
          )}
        </Card.Body>
      </Card>
    </div>
  )
}

export default AdminAppointments
