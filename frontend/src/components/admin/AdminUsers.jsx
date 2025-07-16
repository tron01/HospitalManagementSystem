import { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Card, Table, Button, Badge, Modal, Form, Row, Col } from 'react-bootstrap'
import { fetchUsers, toggleUserStatus } from '../../store/slices/usersSlice'

const AdminUsers = () => {
  const dispatch = useDispatch()
  const { users, loading } = useSelector((state) => state.users)
  const [selectedRole, setSelectedRole] = useState('')
  const [showModal, setShowModal] = useState(false)
  const [selectedUser, setSelectedUser] = useState(null)

  useEffect(() => {
    dispatch(fetchUsers(selectedRole))
  }, [dispatch, selectedRole])

  const handleStatusToggle = (userId, action) => {
    dispatch(toggleUserStatus({ userId, action }))
  }

  const getRoleBadgeColor = (role) => {
    switch (role) {
      case 'admin': return 'danger'
      case 'doctor': return 'primary'
      case 'patient': return 'success'
      case 'receptionist': return 'info'
      default: return 'secondary'
    }
  }

  const getStatusBadgeColor = (status) => {
    switch (status) {
      case 'active': return 'success'
      case 'inactive': return 'warning'
      case 'locked': return 'danger'
      default: return 'secondary'
    }
  }

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h3>User Management</h3>
        <Button variant="primary" onClick={() => setShowModal(true)}>
          Add New User
        </Button>
      </div>

      <Card>
        <Card.Header>
          <Row>
            <Col md={6}>
              <h5>All Users</h5>
            </Col>
            <Col md={6}>
              <Form.Select
                value={selectedRole}
                onChange={(e) => setSelectedRole(e.target.value)}
                size="sm"
              >
                <option value="">All Roles</option>
                <option value="admin">Admin</option>
                <option value="doctor">Doctor</option>
                <option value="patient">Patient</option>
                <option value="receptionist">Receptionist</option>
              </Form.Select>
            </Col>
          </Row>
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
                  <th>Role</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {users?.length > 0 ? (
                  users.map((user) => (
                    <tr key={user.id}>
                      <td>{user.id}</td>
                      <td>{user.name || user.firstName + ' ' + user.lastName}</td>
                      <td>{user.email}</td>
                      <td>
                        <Badge bg={getRoleBadgeColor(user.role)}>
                          {user.role}
                        </Badge>
                      </td>
                      <td>
                        <Badge bg={getStatusBadgeColor(user.status || 'active')}>
                          {user.status || 'active'}
                        </Badge>
                      </td>
                      <td>
                        <div className="btn-group btn-group-sm">
                          <Button
                            variant="outline-primary"
                            size="sm"
                            onClick={() => {
                              setSelectedUser(user)
                              setShowModal(true)
                            }}
                          >
                            Edit
                          </Button>
                          {user.status !== 'locked' ? (
                            <Button
                              variant="outline-danger"
                              size="sm"
                              onClick={() => handleStatusToggle(user.id, 'lock')}
                            >
                              Lock
                            </Button>
                          ) : (
                            <Button
                              variant="outline-success"
                              size="sm"
                              onClick={() => handleStatusToggle(user.id, 'unlock')}
                            >
                              Unlock
                            </Button>
                          )}
                          {user.status !== 'inactive' ? (
                            <Button
                              variant="outline-warning"
                              size="sm"
                              onClick={() => handleStatusToggle(user.id, 'disable')}
                            >
                              Disable
                            </Button>
                          ) : (
                            <Button
                              variant="outline-success"
                              size="sm"
                              onClick={() => handleStatusToggle(user.id, 'enable')}
                            >
                              Enable
                            </Button>
                          )}
                        </div>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="6" className="text-center">
                      No users found
                    </td>
                  </tr>
                )}
              </tbody>
            </Table>
          )}
        </Card.Body>
      </Card>

      {/* User Modal */}
      <Modal show={showModal} onHide={() => setShowModal(false)} size="lg">
        <Modal.Header closeButton>
          <Modal.Title>
            {selectedUser ? 'Edit User' : 'Add New User'}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Row>
              <Col md={6}>
                <Form.Group className="mb-3">
                  <Form.Label>First Name</Form.Label>
                  <Form.Control
                    type="text"
                    defaultValue={selectedUser?.firstName || ''}
                  />
                </Form.Group>
              </Col>
              <Col md={6}>
                <Form.Group className="mb-3">
                  <Form.Label>Last Name</Form.Label>
                  <Form.Control
                    type="text"
                    defaultValue={selectedUser?.lastName || ''}
                  />
                </Form.Group>
              </Col>
            </Row>
            <Form.Group className="mb-3">
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="email"
                defaultValue={selectedUser?.email || ''}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Role</Form.Label>
              <Form.Select defaultValue={selectedUser?.role || 'patient'}>
                <option value="patient">Patient</option>
                <option value="doctor">Doctor</option>
                <option value="receptionist">Receptionist</option>
                <option value="admin">Admin</option>
              </Form.Select>
            </Form.Group>
            {!selectedUser && (
              <Form.Group className="mb-3">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" />
              </Form.Group>
            )}
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowModal(false)}>
            Cancel
          </Button>
          <Button variant="primary">
            {selectedUser ? 'Update User' : 'Create User'}
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  )
}

export default AdminUsers
