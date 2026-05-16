import { useForm } from 'react-hook-form';
import { useDispatch, useSelector } from 'react-redux';
import { login } from '../store/authSlice';

export default function LoginPage() {
  const dispatch = useDispatch();
  const { loading, token, error } = useSelector((state) => state.auth);
  const { register, handleSubmit, formState: { errors } } = useForm();

  return (
    <section>
      <h2>Login</h2>
      <form className="form" onSubmit={handleSubmit((data) => dispatch(login(data)))}>
        <input placeholder="Email" {...register('email', { required: 'Email required' })} />
        {errors.email && <span>{errors.email.message}</span>}
        <input placeholder="Password" type="password" {...register('password', { required: 'Password required', minLength: 6 })} />
        <button disabled={loading} type="submit">Sign in</button>
      </form>
      {token && <p>Authorized successfully.</p>}
      {error && <p>{error}</p>}
    </section>
  );
}
