import { useNavigate } from 'react-router-dom'
import Navbar from '../../components/Navbar'

function FacultyDashboard() {
  const navigate = useNavigate()
  const user = JSON.parse(localStorage.getItem('user'))

  const handleLogout = () => {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    navigate('/login')
  }

  return (
    <div>
      <Navbar onLogout={handleLogout} />
      
      <div className="container mt-4">
        <h2 className="mb-4">Faculty Dashboard</h2>
        
        <div className="alert alert-info">
          <strong>Welcome, {user?.email}</strong>
        </div>

        <div className="row">
          <div className="col-md-4">
            <div className="card dashboard-card bg-primary text-white">
              <div className="card-body">
                <h5 className="card-title">Assigned Courses</h5>
                <h2 className="card-text">0</h2>
              </div>
            </div>
          </div>
          
          <div className="col-md-4">
            <div className="card dashboard-card bg-success text-white">
              <div className="card-body">
                <h5 className="card-title">Total Students</h5>
                <h2 className="card-text">0</h2>
              </div>
            </div>
          </div>
          
          <div className="col-md-4">
            <div className="card dashboard-card bg-info text-white">
              <div className="card-body">
                <h5 className="card-title">Classes Today</h5>
                <h2 className="card-text">0</h2>
              </div>
            </div>
          </div>
        </div>

        <div className="row mt-4">
          <div className="col-md-12">
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">Quick Actions</h5>
                <div className="d-flex gap-2 flex-wrap">
                  <button className="btn btn-primary">View Assigned Courses</button>
                  <button className="btn btn-success">Mark Attendance</button>
                  <button className="btn btn-info">View Students</button>
                  <button className="btn btn-warning">View Attendance Reports</button>
                  <button className="btn btn-secondary">View Timetable</button>
                  <button className="btn btn-dark">Mark Own Attendance</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default FacultyDashboard
