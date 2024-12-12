import React, { useEffect, useState } from 'react';
import { Employee } from '../models/Employee';
import SortButton from '../components/sortButton';


function Home() {

  const [employees, setEmployees] = useState<Employee[]>(null);
  const [loading, setLoading] = useState(true);

  const [sortOrder, setSortOrder] = useState<'asc' | 'desc' | null>(null);
  const [sortColumn, setSortColumn] = useState<keyof Employee | null>(null);
  const handleSort = (column: keyof Employee) => {
    if (sortColumn === column) {
      setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc');
    } else {
      setSortColumn(column);
      setSortOrder('asc');
    }
  };

  const sortedData = employees?.sort((a, b) => {
    if (!sortColumn) return 0;

    if (a[sortColumn] < b[sortColumn]) {
      return sortOrder === 'asc' ? -1 : 1;
    } else if (a[sortColumn] > b[sortColumn]) {
      return sortOrder === 'asc' ? 1 : -1;
    } else {
      return 0;
    }
  });


  useEffect(() => {
    retreiveEmployee();
  }, []);

  const retreiveEmployee = async () => {
    try {
      const response = await fetch('http://localhost:8080/employee');
      if (!response.ok) {
        throw new Error('Failed to fetch employees');
      }

      const eData: Employee[] = await response.json();
      console.log(eData);
      setEmployees(eData);
    } catch (error) {
      console.error('Error fetching employees:', error);
    } finally {
      setLoading(false);
    }
  };
  
  return (
    <>
      <nav className="navbar navbar-dark bg-dark">
        <span className='title-text white title-padding'>Employee List</span>
      </nav>
          {loading && <p>Loading...</p>}
          {!loading && 
          <table className="table table-striped">
          <thead>
            <tr>
              <th onClick={() => handleSort('id')} scope="col">Id {SortButton("id", sortColumn, sortOrder)}</th>
              <th onClick={() => handleSort('name')} scope="col">Name {SortButton("name", sortColumn, sortOrder)}</th>
              <th onClick={() => handleSort('position')} scope="col">Position {SortButton("position", sortColumn, sortOrder)}</th>
              <th scope="col">Hire Date</th>
              <th scope="col"></th>
            </tr>
          </thead>
          <tbody>
            
            {employees.map((employee: Employee) => (
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
          }
    </>
  );
}

export default Home;