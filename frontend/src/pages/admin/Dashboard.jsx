import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import api from '../../utils/api'
import Navbar from '../../components/Navbar'

function AdminDashboard() {
  const [stats, setStats] = useState(null)
  const [loading, setLoading] = useState(true)
  const navigate = useNavigate()

  useEffect(() => {
    fetchDashboardStats()
  }, [])

  const fetchDashboardStats = async () => {
    try {
      const response = await api.get('/dashboard/admin')
      setStats(response.data)
    } catch (err) {
      console.error('Failed to fetch dashboard stats:', err)
    } finally {
      setLoading(false)
    }
  }

  const handleLogout = () => {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    navigate('/login')
  }

  if (loading) {
    return <div className="container mt-5">Loading...</div>
  }

  return (
    <div>
      <Navbar onLogout={handleLogout} />
      
      <div className="container mt-4">
        <h2 className="mb-4">Admin Dashboard</h2>
        
        <div className="row">
          <div className="col-md-3">
            <div className="card dashboard-card bg-primary text-white">
              <div className="card-body">
                <h5 className="card-title">Total Students</h5>
                <h2 className="card-text">{stats?.totalStudents || 0}</h2>
              </div>
            </div>
          </div>
          
          <div className="col-md-3">
            <div className="card dashboard-card bg-success text-white">
              <div className="card-body">
                <h5 className="card-title">Total Faculty</h5>
                <h2 className="card-text">{stats?.totalFaculty || 0}</h2>
              </div>
            </div>
          </div>
          
          <div className="col-md-3">
            <div className="card dashboard-card bg-info text-white">
              <div className="card-body">
                <h5 className="card-title">Total Courses</h5>
                <h2 className="card-text">{stats?.totalCourses || 0}</h2>
              </div>
            </div>
          </div>
          
          <div className="col-md-3">
            <div className="card dashboard-card bg-warning text-white">
              <div className="card-body">
                <h5 className="card-title">Active Students</h5>
                <h2 className="card-text">{stats?.activeStudents || 0}</h2>
              </div>
            </div>
          </div>
        </div>

        <div className="row mt-4">
          <div className="col-md-6">
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">Average Attendance</h5>
                <h3 className="card-text">{stats?.averageAttendance?.toFixed(2) || 0}%</h3>
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
                  <button className="btn btn-primary">Manage Students</button>
                  <button className="btn btn-success">Manage Faculty</button>
                  <button className="btn btn-info">Manage Courses</button>
                  <button className="btn btn-warning">View Reports</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default AdminDashboard
