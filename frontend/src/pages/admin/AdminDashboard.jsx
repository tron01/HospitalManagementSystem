import { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Routes, Route } from 'react-router-dom'
import { Row, Col, Card } from 'react-bootstrap'
import { fetchDashboardData } from '../../store/slices/dashboardSlice'
import AdminUsers from '../../components/admin/AdminUsers'
import AdminAppointments from '../../components/admin/AdminAppointments'
import AdminPatients from '../../components/admin/AdminPatients'
import AdminDoctors from '../../components/admin/AdminDoctors'

const AdminDashboard = () => {
  const dispatch = useDispatch()
  const { data: dashboardData, loading } = useSelector((state) => state.dashboard)

  useEffect(() => {
    dispatch(fetchDashboardData('admin'))
  }, [dispatch])

  const DashboardHome = () => (
    <div>
      <h2 className="mb-4">Admin Dashboard</h2>
      <Row>
        <Col xs={12} sm={6} md={3}>
          <Card className="text-center mb-3">
            <Card.Body>
              <Card.Title className="text-primary">
                {dashboardData?.totalUsers || 0}
              </Card.Title>
              <Card.Text>Total Users</Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col xs={12} sm={6} md={3}>
          <Card className="text-center mb-3">
            <Card.Body>
              <Card.Title className="text-success">
                {dashboardData?.totalPatients || 0}
              </Card.Title>
              <Card.Text>Total Patients</Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col xs={12} sm={6} md={3}>
          <Card className="text-center mb-3">
            <Card.Body>
              <Card.Title className="text-info">
                {dashboardData?.totalDoctors || 0}
              </Card.Title>
              <Card.Text>Total Doctors</Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col xs={12} sm={6} md={3}>
          <Card className="text-center mb-3">
            <Card.Body>
              <Card.Title className="text-warning">
                {dashboardData?.totalAppointments || 0}
              </Card.Title>
              <Card.Text>Total Appointments</Card.Text>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <Row>
        <Col xs={12} lg={6} className="mb-3">
          <Card>
            <Card.Header>
              <h5>Recent Appointments</h5>
            </Card.Header>
            <Card.Body>
              {dashboardData?.recentAppointments?.length > 0 ? (
                <div className="list-group list-group-flush">
                  {dashboardData.recentAppointments.slice(0, 5).map((appointment, index) => (
                    <div key={index} className="list-group-item">
                      <div className="d-flex justify-content-between">
                        <span>{appointment.patientName}</span>
                        <small className="text-muted">{appointment.date}</small>
                      </div>
                      <small className="text-muted">Dr. {appointment.doctorName}</small>
                    </div>
                  ))}
                </div>
              ) : (
                <p className="text-muted">No recent appointments</p>
              )}
            </Card.Body>
          </Card>
        </Col>
        <Col xs={12} lg={6} className="mb-3">
          <Card>
            <Card.Header>
              <h5>System Status</h5>
            </Card.Header>
            <Card.Body>
              <div className="mb-3">
                <div className="d-flex justify-content-between">
                  <span>Active Users</span>
                  <span className="badge bg-success">
                    {dashboardData?.activeUsers || 0}
                  </span>
                </div>
              </div>
              <div className="mb-3">
                <div className="d-flex justify-content-between">
                  <span>Pending Appointments</span>
                  <span className="badge bg-warning">
                    {dashboardData?.pendingAppointments || 0}
                  </span>
                </div>
              </div>
              <div className="mb-3">
                <div className="d-flex justify-content-between">
                  <span>Completed Today</span>
                  <span className="badge bg-primary">
                    {dashboardData?.completedToday || 0}
                  </span>
                </div>
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
      <Route path="/users" element={<AdminUsers />} />
      <Route path="/appointments" element={<AdminAppointments />} />
      <Route path="/patients" element={<AdminPatients />} />
      <Route path="/doctors" element={<AdminDoctors />} />
    </Routes>
  )
}

export default AdminDashboard
