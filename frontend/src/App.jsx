import { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

function App() {
    const [jobs, setJobs] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/api/jobs')
            .then(response => {
                console.log('Dados recebidos da API:', response.data);
                setJobs(response.data);
            })
            .catch(error => {
                console.error('Houve um erro ao buscar as vagas:', error);
            });
    }, []);

    return (
        <>
            <h1>Gupyfy Job Hunter 🚀</h1>
            <h2>Vagas Encontradas:</h2>
            <ul>
                {}
                {jobs.map(job => (
                    <li key={job.id}>
                        {job.title} - {job.companyName}
                    </li>
                ))}
            </ul>
        </>
    );
}

export default App;