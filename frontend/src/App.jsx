import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navigation from './components/Navigation';
import EmployeeList from './pages/EmployeeList';
import DeletedRecords from './pages/DeletedRecords';
import AuditLogs from './pages/AuditLogs';

function App() {
  return (
    <Router>
      <div className="min-h-screen">
        <Navigation />
        <main className="container mx-auto px-4 py-8 max-w-7xl">
          <Routes>
            <Route path="/" element={<EmployeeList />} />
            <Route path="/deleted" element={<DeletedRecords />} />
            <Route path="/audit-logs" element={<AuditLogs />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
