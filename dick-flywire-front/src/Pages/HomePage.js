// src/HomePage.js
import React, { useEffect, useState } from 'react';

const HomePage = () => {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Fetch employee data from the API
    fetch('http://localhost:8080/employee')
      .then((response) => response.json())
      .then((data) => {
        setEmployees(data);
        setLoading(false);
      })
      .catch((err) => {
        setError('Failed to fetch employee data');
        setLoading(false);
      });
  }, []);

  return (
    <>
        <nav className="navbar navbar-dark bg-dark">
            <span className='title-text white title-padding'>Employee List</span>      
        </nav>
        {loading && <p>Loading...</p>}
        {error && <p>{error}</p>}
        {/* <div className="container"> */}
            <div className='row'>
                
            <table class="table table-striped">
            <thead>
                <tr>
                <th scope="col">Id</th>
                <th scope="col">Name</th>
                <th scope="col">Position</th>
                <th scope="col">Hire Date</th>
                <th scope="col"></th>
                </tr>
            </thead>
            <tbody>
                    {employees.map((employee) => (
                        <tr key={employee.id}>
                          <th scope="row">{employee.id}</th>
                          <td>{employee.name}</td>
                          <td>{employee.position}</td>
                          <td>{employee.hireDate}</td>
                          <td>Details</td>
                        </tr>
                    ))}
                    
                    </tbody>
                </table>
                </div>
            {/* </div> */}
        

    </>
  );
};
//"active":true,"directReports":[],"hireDate":"10/12/2018","id":24,"name":"John Beige","position":"Accountant"}
export default HomePage;
