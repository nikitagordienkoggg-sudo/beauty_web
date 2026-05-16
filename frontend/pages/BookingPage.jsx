import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { bookingApi } from '../api/bookingApi';

export default function BookingPage() {
  const [message, setMessage] = useState('');
  const { register, handleSubmit, formState: { errors } } = useForm();

  const onSubmit = async (values) => {
    try {
      await bookingApi.createBooking({
        clientId: Number(values.clientId),
        serviceId: Number(values.serviceId),
        masterId: Number(values.masterId),
        timeSlotId: Number(values.timeSlotId)
      });
      setMessage('Booking created successfully');
    } catch {
      setMessage('Failed to create booking');
    }
  };

  return (
    <section>
      <h2>Create booking</h2>
      <form className="form" onSubmit={handleSubmit(onSubmit)}>
        <input placeholder="Client ID" {...register('clientId', { required: 'Required', min: 1 })} />
        {errors.clientId && <span>{errors.clientId.message}</span>}
        <input placeholder="Service ID" {...register('serviceId', { required: 'Required', min: 1 })} />
        <input placeholder="Master ID" {...register('masterId', { required: 'Required', min: 1 })} />
        <input placeholder="Time Slot ID" {...register('timeSlotId', { required: 'Required', min: 1 })} />
        <button type="submit">Book now</button>
      </form>
      <p>{message}</p>
    </section>
  );
}
