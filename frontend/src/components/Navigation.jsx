import { Link, useLocation } from 'react-router-dom';
import { Users, Trash2, ShieldAlert } from 'lucide-react';

function Navigation() {
    const location = useLocation();

    const navItems = [
        { path: '/', label: 'Employees', icon: Users },
        { path: '/deleted', label: 'Deleted Records', icon: Trash2 },
        { path: '/audit-logs', label: 'Audit Logs', icon: ShieldAlert },
    ];

    return (
        <nav className="bg-white shadow">
            <div className="container mx-auto px-4 max-w-7xl">
                <div className="flex space-x-8 h-16">
                    {navItems.map(({ path, label, icon: Icon }) => (
                        <Link
                            key={path}
                            to={path}
                            className={`inline-flex items-center px-1 border-b-2 text-sm font-medium ${location.pathname === path
                                    ? 'border-indigo-500 text-gray-900'
                                    : 'border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700'
                                }`}
                        >
                            <Icon className="w-5 h-5 mr-2" />
                            {label}
                        </Link>
                    ))}
                </div>
            </div>
        </nav>
    );
}

export default Navigation;
