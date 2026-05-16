import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchServices } from '../store/servicesSlice';

export default function ServicesPage() {
  const dispatch = useDispatch();
  const { items, loading } = useSelector((state) => state.services);
  const [query, setQuery] = useState('');

  useEffect(() => {
    dispatch(fetchServices());
  }, [dispatch]);

  return (
    <section>
      <h2>Services</h2>
      <div className="row">
        <input value={query} onChange={(e) => setQuery(e.target.value)} placeholder="Search service" />
        <button onClick={() => dispatch(fetchServices(query))}>Search</button>
      </div>
      {loading && <p>Loading...</p>}
      <ul className="card-grid">
        {items.map((service) => (
          <li key={service.id} className="card">
            <strong>{service.name}</strong>
            <p>Price: {service.price}</p>
            <p>Duration: {service.durationMinutes} min</p>
          </li>
        ))}
      </ul>
    </section>
  );
}
