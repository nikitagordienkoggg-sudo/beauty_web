import { NavLink } from 'react-router-dom';
import './Header.css';

const links = [
  ['/', 'Home'],
  ['/services', 'Services'],
  ['/booking', 'New booking'],
  ['/bookings', 'My bookings'],
  ['/payments', 'Payments'],
  ['/login', 'Login']
];

export default function Header() {
  return (
    <header className="header">
      <div className="brand">Beauty Booking</div>
      <nav className="nav">
        {links.map(([to, label]) => (
          <NavLink key={to} className="link" to={to}>
            {label}
          </NavLink>
        ))}
      </nav>
    </header>
  );
}
