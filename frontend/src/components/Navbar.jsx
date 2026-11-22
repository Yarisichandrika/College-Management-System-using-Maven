function Navbar({ onLogout }) {
  const user = JSON.parse(localStorage.getItem('user'))

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <div className="container">
        <a className="navbar-brand" href="/">College Management System</a>
        
        <div className="navbar-nav ms-auto">
          <span className="navbar-text me-3">
            Welcome, {user?.email} ({user?.role})
          </span>
          <button className="btn btn-outline-light btn-sm" onClick={onLogout}>
            Logout
          </button>
        </div>
      </div>
    </nav>
  )
}

export default Navbar
