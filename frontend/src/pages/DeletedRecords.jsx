import { useState, useEffect } from 'react';
import { RotateCcw } from 'lucide-react';
import api from '../api';

function DeletedRecords() {
    const [employees, setEmployees] = useState([]);

    useEffect(() => {
        fetchDeletedEmployees();
    }, []);

    const fetchDeletedEmployees = async () => {
        try {
            const { data } = await api.get('/employees/deleted');
            setEmployees(data);
        } catch (error) {
            console.error('Error fetching deleted employees:', error);
        }
    };

    const handleRestore = async (id) => {
        if (!window.confirm('Are you sure you want to restore this employee?')) return;
        try {
            await api.post(`/employees/${id}/restore`);
            fetchDeletedEmployees();
        } catch (error) {
            console.error('Error restoring employee:', error);
        }
    };

    return (
        <div className="space-y-6">
            <div className="flex justify-between items-center">
                <h1 className="text-2xl font-bold text-gray-900">Deleted Records</h1>
            </div>

            <div className="bg-white shadow-md rounded-lg overflow-hidden">
                <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-50">
                        <tr>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Department</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Salary</th>
                            <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                        </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                        {employees.map((employee) => (
                            <tr key={employee.id}>
                                <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{employee.name}</td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{employee.department}</td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">${employee.salary}</td>
                                <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                                    <button
                                        onClick={() => handleRestore(employee.id)}
                                        className="inline-flex items-center text-green-600 hover:text-green-900 font-medium"
                                    >
                                        <RotateCcw className="w-4 h-4 mr-1" />
                                        Restore
                                    </button>
                                </td>
                            </tr>
                        ))}
                        {employees.length === 0 && (
                            <tr>
                                <td colSpan="4" className="px-6 py-4 text-center text-gray-500">No deleted records found.</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default DeletedRecords;
