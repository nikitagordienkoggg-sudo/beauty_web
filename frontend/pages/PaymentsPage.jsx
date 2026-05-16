import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { bookingApi } from '../api/bookingApi';

export default function PaymentsPage() {
  const [result, setResult] = useState('');
  const { register, handleSubmit } = useForm();

  const onSubmit = async (data) => {
    try {
      const { data: payment } = await bookingApi.pay({ bookingId: Number(data.bookingId), amount: Number(data.amount) });
      setResult(`Payment #${payment.id} status: ${payment.status}`);
    } catch {
      setResult('Payment failed');
    }
  };

  return (
    <section>
      <h2>Payments</h2>
      <form className="form" onSubmit={handleSubmit(onSubmit)}>
        <input placeholder="Booking ID" {...register('bookingId', { required: true, min: 1 })} />
        <input placeholder="Amount" {...register('amount', { required: true, min: 1 })} />
        <button type="submit">Pay</button>
      </form>
      <p>{result}</p>
    </section>
  );
}
