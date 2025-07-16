import { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Card, Table, Button } from 'react-bootstrap'
import { fetchPatients } from '../../store/slices/patientsSlice'

const AdminPatients = () => {
  const dispatch = useDispatch()
  const { patients, loading } = useSelector((state) => state.patients)

  useEffect(() => {
    dispatch(fetchPatients())
  }, [dispatch])

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h3>Patient Management</h3>
        <Button variant="primary">
          Add New Patient
        </Button>
      </div>

      <Card>
        <Card.Header>
          <h5>All Patients</h5>
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
                  <th>Phone</th>
                  <th>Age</th>
                  <th>Last Visit</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {patients?.length > 0 ? (
                  patients.map((patient, index) => (
                    <tr key={patient.id || index}>
                      <td>{patient.id || index + 1}</td>
                      <td>{patient.name || patient.firstName + ' ' + patient.lastName}</td>
                      <td>{patient.email}</td>
                      <td>{patient.phone || 'N/A'}</td>
                      <td>{patient.age || 'N/A'}</td>
                      <td>{patient.lastVisit || 'N/A'}</td>
                      <td>
                        <div className="btn-group btn-group-sm">
                          <Button variant="outline-primary" size="sm">
                            View
                          </Button>
                          <Button variant="outline-success" size="sm">
                            Edit
                          </Button>
                          <Button variant="outline-info" size="sm">
                            History
                          </Button>
                        </div>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="7" className="text-center">
                      No patients found
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

export default AdminPatients
