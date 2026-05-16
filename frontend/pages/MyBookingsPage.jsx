import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchBookings } from '../store/bookingsSlice';

export default function MyBookingsPage() {
  const [clientId, setClientId] = useState('');
  const dispatch = useDispatch();
  const { items, loading } = useSelector((state) => state.bookings);

  return (
    <section>
      <h2>My bookings</h2>
      <div className="row">
        <input value={clientId} onChange={(e) => setClientId(e.target.value)} placeholder="Client ID" />
        <button onClick={() => dispatch(fetchBookings(Number(clientId)))}>Load</button>
      </div>
      {loading ? <p>Loading...</p> : (
        <ul>
          {items.map((booking) => <li key={booking.id}>#{booking.id} | {booking.status} | {booking.dateTime}</li>)}
        </ul>
      )}
    </section>
  );
}
