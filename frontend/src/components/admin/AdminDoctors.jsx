import { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Card, Table, Button, Badge } from 'react-bootstrap'
import { fetchDoctors } from '../../store/slices/doctorsSlice'

const AdminDoctors = () => {
  const dispatch = useDispatch()
  const { doctors, loading } = useSelector((state) => state.doctors)

  useEffect(() => {
    dispatch(fetchDoctors())
  }, [dispatch])

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h3>Doctor Management</h3>
        <Button variant="primary">
          Add New Doctor
        </Button>
      </div>

      <Card>
        <Card.Header>
          <h5>All Doctors</h5>
        </Card.Header>
        <Card.Body>
          {loading ? (
            <div className="text-center">Loading...</div>
          ) : (
            <Table striped bordered hover responsive>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Specialization</th>
                  <th>Department</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {doctors?.length > 0 ? (
                  doctors.map((doctor, index) => (
                    <tr key={doctor.id || index}>
                      <td>{doctor.id || index + 1}</td>
                      <td>{doctor.name || doctor.firstName + ' ' + doctor.lastName}</td>
                      <td>{doctor.email}</td>
                      <td>{doctor.specialization || 'General Medicine'}</td>
                      <td>{doctor.department || 'Internal Medicine'}</td>
                      <td>
                        <Badge bg={doctor.status === 'active' ? 'success' : 'secondary'}>
                          {doctor.status || 'active'}
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
                          <Button variant="outline-info" size="sm">
                            Schedule
                          </Button>
                        </div>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="7" className="text-center">
                      No doctors found
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

export default AdminDoctors
