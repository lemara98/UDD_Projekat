import React from 'react';
import { Navbar } from 'react-bootstrap';
import { NavLink } from 'react-router-dom';

const MainBar = () => {
  return (
    <Navbar bg="dark" 
    variant="dark" 
    style={{maxHeight:'50px'}} 
    className="shadow-lg p-4">
    <Navbar.Brand className="d-flex">
        <NavLink to="/">
            UDD React Frontend Search Application
        </NavLink>
        <div className="mx-5">
            |
        </div>
        <NavLink to="/upload">
            Upload Page
        </NavLink>
        <div className="mx-5">
            |
        </div>
        <NavLink to="/search">
            Search Page
        </NavLink>
    </Navbar.Brand>
</Navbar>
  );
};

export default MainBar;
