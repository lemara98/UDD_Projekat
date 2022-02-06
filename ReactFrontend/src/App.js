import './App.css';
import { useState } from 'react';
import { useEffect } from 'react';
import axios from 'axios';


function App() {
  const [selectedValue, setSelectedValue] = useState(`1`);
  const searchControllerURL = "http://localhost:8080/api/search";

  // criteria
  // const [name, setName] = useState("");
  // const [lastName, setLastName] = useState("");

  // results
  const [candidateResults, setCandidateResults] = useState();
  const [cvResults, setCvResults] = useState();

  // error
  const [errorMessage, setErrorMessage] = useState("");
  // rest

  useEffect(() => {

  });
  
  function search() {
    switch (selectedValue) {
      case "1":
        let nameAndLastNameDTO = {
            firstName: document.getElementById("textbox_name").value,
            lastName: document.getElementById("textbox_lastName").value
        }
        axios.post(`${searchControllerURL}/2.1`, nameAndLastNameDTO)
        .then((resp) => {
            setCandidateResults(resp.data);
            setErrorMessage("");
        })
        .catch(() => {
          setErrorMessage("Something went wrong during the search by: Name and LastName");
        })
        break;
      case "2":
        let educationDTO = {
          education: document.getElementById("textbox_education").value
        }
        axios.post(`${searchControllerURL}/2.2`, educationDTO)
        .then((resp) => {
            setCandidateResults(resp.data);
            setErrorMessage("");
        })
        .catch(() => {
          setErrorMessage("Something went wrong during the search by: Name and LastName");
        })        
        break;
      case "3":
    
        break;
      case "4":
    
        break;
      case "5":
    
        break;
      case "6":
    
        break;
      case "7":
    
        break;
      case "8":
  
        break;

      default:
        console.log("Something went wrong")
        break;
    }
  }


  function changeSelectedValue(event) {
    setSelectedValue(event.target.value.toString());
  }

  function QueryDOM() {
    switch (selectedValue) {
      case "1":
        return (
          <div className='row' >
            {/* This form is used for: 2.1 Pretraživanje aplikanata po imenu i prezimenu */}
            <div className='container'>
              <div className='d-flex my-2'>
                <div className='col-6'>
                  <h5 className='mt-2'>Ime:</h5>
                </div>
                <div className='col-6'>
                  <input id='textbox_name' type="text" className='form-control'></input>
                </div>
              </div>
              <div className='d-flex my-2'>
                <div className='col-6'>
                  <h5 className='mt-2'>Prezime:</h5>
                </div>
                <div className='col-6'>
                  <input id='textbox_lastName' type="text" className='form-control'></input>
                </div>
              </div>
            </div>
          </div>
        );
        case "2":
          return (
            <div className='row' >
              {/* This form is used for: 2.1 Pretraživanje aplikanata po imenu i prezimenu */}
              <div className='container'>
                <div className='d-flex my-2'>
                  <div className='col-6'>
                    <h5 className='mt-2'>Obrazovanje:</h5>
                  </div>
                  <div className='col-6'>
                    <select id='textbox_education' className='form-control'>
                      <option value="Osnovno">Osnovno</option> 
                      <option value="Srednje">Srednje</option>
                      <option value="Vise">Više</option>
                      <option value="Visoko">Visoko</option>
                      <option value="Specijalisticko">Specijalisticko</option>
                    </select>
                  </div>
                </div>
              </div>
            </div>
          );
    
      default:
        return (<div>{selectedValue}</div>);
    }
  }

  function ResultsDOM() {
    if (candidateResults === undefined || candidateResults === null || candidateResults === "") return (<></>);
    switch (selectedValue) {
      case "1":
        return (
          <table  className="table table-hover mt-3 bg-light ">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Name</th>
                    <th scope="col">LastName</th>
                    <th scope="col">Date of Birth</th>
                    <th scope="col">Phone Number</th>
                    <th scope="col">Education</th>
                    <th scope="col">Working experience [years]</th>
                </tr>
            </thead>
            <tbody>
                { candidateResults.map((o, i) => (
                <tr key={i} style={{cursor:`pointer`}}>
                    <th scope="row">{i+1}</th>
                    <td>{o.firstname}</td>
                    <td>{o.lastname}</td>
                    <td>{new Intl.DateTimeFormat('sr-Latn-CS', {year: 'numeric', month: '2-digit',day: '2-digit'}).format(new Date(o.dateOfBirth)) }</td>
                    <td>{o.phoneNumber}</td>
                    <td>{o.education}</td>
                    <td>{o.workingExperience}</td>
                </tr>
                ))
                }
            </tbody>
          </table>
        );
        case "2":
          return (
            <table  className="table table-hover mt-3 bg-light ">
              <thead>
                  <tr>
                      <th scope="col">#</th>
                      <th scope="col">FirstName</th>
                      <th scope="col">LastName</th>
                      <th scope="col">Date of Birth</th>
                      <th scope="col">Phone Number</th>
                      <th scope="col">Education</th>
                      <th scope="col">Working experience [years]</th>
                  </tr>
              </thead>
              <tbody>
                  { candidateResults.map((o, i) => (
                  <tr key={i} style={{cursor:`pointer`}}>
                      <th scope="row">{i+1}</th>
                      <td>{o.firstname}</td>
                      <td>{o.lastname}</td>
                      <td>{new Intl.DateTimeFormat('sr-Latn-CS', {year: 'numeric', month: '2-digit',day: '2-digit'}).format(new Date(o.dateOfBirth)) }</td>
                      <td>{o.phoneNumber}</td>
                      <td>{o.education}</td>
                      <td>{o.workingExperience}</td>
                  </tr>
                  ))
                  }
              </tbody>
            </table>
          );
    
      default:
        return (<div>{selectedValue}</div>);
    }
  }

  return (
    <div className="App">
      <div className='container'> 
        <div className='row mt-3'>
          <div className='col-4'/>
          <div className='col-4'>
            <h1>ELK UDD Projekat</h1>
          </div>
          <div className='col-4'/>
        </div>
      </div>
      <div className='container'> 
        <div className='row mt-3'>
          <div className='col-6'>
            <div className='row'>
              <h4>Kriterijumi pretrage:</h4>
            </div>
            <div className='row mt-2'>
              <select className='form-control' onChange={changeSelectedValue}>
                <option value="1">2.1 Pretraživanje aplikanata po imenu i prezimenu</option> 
                <option value="2">2.2 Pretraživanje aplikanata prema obrazovanju (stepenu stručne spreme)</option>
                <option value="3">2.3 Pretraživanje prema sadržaju CV dokumenta (iz PDF fajla)</option>
                <option value="4">2.4 Kombinacija prethodnih parametara pretrage (BooleanQuery, omogućiti i AND i OR operator između polja)</option>
                <option value="5">2.5 Obezbediti podršku i za zadavanje PhrazeQuery-a u svim poljima</option>
                <option value="6">2.6 Pretprocesirati upit pomoću SerbianAnalyzer-a</option>
                <option value="7">2.7 Prilikom prikaza rezultata kreirati dinamički sažetak (Highlighter)</option>
                <option value="8">2.8 Pretraživanje po Geolokaciji</option>
              </select>
            </div>
            <hr style={{border:"3px solid"}}></hr>
            {/* This part is the form part, it is dependent upon the value selected in <select> tag */}
            <QueryDOM/>
            {/* Button Pretrazi part */}
            <div className='row mt-2'>
              <div className='col-5'></div>
              <div className='col-5'>
                <button className='form-control' style={{width:"100px"}} onClick={search}>Pretraži</button>
                <div className='bg-danger text-white my-3'>{errorMessage}</div>
              </div>
              <div className='col-5'></div>
              
            </div>
          </div>
          {/* Deo za ispisivanje rezultata */}
          <div className='col-6'>
            <ResultsDOM/>
          </div>
        </div>
      </div>
      
    </div>
  );
}

export default App;
