import React from 'react';
import { useState } from 'react';
import axios from 'axios';

const SearchPage = () => {
    const [selectedValue, setSelectedValue] = useState(`1`);
    const searchControllerURL = "http://localhost:8080/api/search";

    // Criteria Values
    // const [name, setName] = useState("");
    // const [lastName, setLastName] = useState("")
    // const [education, setEducation] = useState("Osnovno");



    // results
    const [candidateResults, setCandidateResults] = useState();

    // error
    const [errorMessage, setErrorMessage] = useState("");
    const [successMessage, setSuccessMessage] = useState("");
    // rest

    function changeSelectedValue(event) {
        setCandidateResults(undefined);
        setSelectedValue(event.target.value.toString());
      }


    function geocoding() {
        axios.get(`https://nominatim.openstreetmap.org/search/${document.getElementById("textbox_city").value}?format=json&limit=1`) 
        .then((resp) => {
          document.getElementById("textbox_lon").value = resp.data[0].lon;
          document.getElementById("textbox_lat").value = resp.data[0].lat;
        })
        .catch((error) => {
          console.log(error);
        })
    }
    
    function ResultTable() {
      if (candidateResults === undefined) return;
        return (
            <table  className="table table-hover mt-3 bg-light ">
                  <thead>
                      <tr>
                          <th scope="col">#</th>
                          <th scope="col">FirstName</th>
                          <th scope="col">LastName</th>
                          <th scope="col">Date of Birth</th>
                          <th scope="col">City</th>
                          <th scope="col">Phone Number</th>
                          <th scope="col">Education</th>
                          <th scope="col">Working experience [years]</th>
                          <th scope="col">CV</th>
                      </tr>
                  </thead>
                  <tbody>
                      { candidateResults.map((o, i) => (
                      <tr key={i} style={{cursor:`pointer`}}>
                          <th scope="row">{i+1}</th>
                          <td>{o.content.candidate.firstname}</td>
                          <td>{o.content.candidate.lastname}</td>
                          <td>{new Intl.DateTimeFormat('sr-Latn-CS', {year: 'numeric', month: '2-digit',day: '2-digit'}).format(new Date(o.content.candidate.dateOfBirth[0], o.content.candidate.dateOfBirth[1] - 1, o.content.candidate.dateOfBirth[2]))}</td>
                          <td>{o.content.candidate.city}</td>
                          <td>{o.content.candidate.phoneNumber}</td>
                          <td>{o.content.candidate.education}</td>
                          <td>{o.content.candidate.workingExperience}</td>
                          <td><button type="button" className="btn btn-dark" onClick={() => {window.open(`/documents/${o.content.fileName}`)}}>Download CV</button></td>
                      </tr>
                      ))
                      }
                  </tbody>
            </table>
        )
    }

    function ResultTableComplex() {
      if (candidateResults === undefined) return;
      return (
          <table  className="table table-hover mt-3 bg-light ">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">FirstName</th>
                        <th scope="col">LastName</th>
                        <th scope="col">Date of Birth</th>
                        <th scope="col">City</th>
                        <th scope="col">Phone Number</th>
                        <th scope="col">Education</th>
                        <th scope="col">Working experience [years]</th>
                        <th scope="col">CV</th>
                    </tr>
                </thead>
                <tbody>
                    { candidateResults !== [] ? candidateResults.map((o, i) => (
                    <tr key={i} style={{cursor:`pointer`}}>
                        <th scope="row">{i+1}</th>
                        <td>{o._source.candidate.firstname}</td>
                        <td>{o._source.candidate.lastname}</td>
                        <td>{new Intl.DateTimeFormat('sr-Latn-CS', {year: 'numeric', month: '2-digit',day: '2-digit'}).format(new Date(o._source.candidate.dateOfBirth[0], o._source.candidate.dateOfBirth[1] - 1, o._source.candidate.dateOfBirth[2]))}</td>
                        <td>{o._source.candidate.city}</td>
                        <td>{o._source.candidate.phoneNumber}</td>
                        <td>{o._source.candidate.education}</td>
                        <td>{o._source.candidate.workingExperience}</td>
                        <td><button type="button" className="btn btn-dark" onClick={() => {window.open(`/documents/${o._source.fileName}`)}}>Download CV</button></td>
                    </tr>
                    ))
                    :
                    null
                    }
                </tbody>
          </table>
      )
  }

    function ResultHighLight() {
      if (candidateResults === undefined) return;
      return (
              <div className='mt-3 bg-gray'>
                  { candidateResults.map((o, i1) => (
                  <div key={i1}>
                      { 
                        o.highlightFields['content.sr'].map((hl, i2) => (
                        <div key={hl}>
                          <h4>Korisnik #{i1+1}, Mesto #{i2+1}</h4>
                          <p className="hl" dangerouslySetInnerHTML={{__html: htmlEscape(hl)}}></p>
                        </div>
                        ))
                      }
                  </div>
                  ))
                  }
              </div>
      )
  }

  function ResultHighLightComplex() {
    if (candidateResults === undefined) return;
    return (
            <div className='mt-3 bg-gray'>
                { candidateResults !== [] ? candidateResults.map((o, i1) => (
                <div key={i1}>
                    { 
                      o.highlight !== undefined ? o.highlight['content.sr'].map((hl, i2) => (
                      <div key={i2}>
                        <h4>Korisnik #{i1+1}, Mesto #{i2+1}</h4>
                        <p className="hl" dangerouslySetInnerHTML={{__html: htmlEscape(hl)}}></p>
                      </div>
                      ))
                      :
                      null
                    }
                </div>
                ))
                :
                null
                }
            </div>
    )
}

  function htmlEscape(str) {
    var parser = new DOMParser();
    let str1 = parser.parseFromString(str, 'text/html').body.innerHTML;
    let str2 = str1.replace(`<em>`, `<em><strong>`).replace(`</em>`, `</strong></em>`);
    return str2;
}
    

    function search() {
        switch (selectedValue) {
          case "1":
            let nameAndLastNameDTO = {
                firstName: document.getElementById("textbox_name").value,
                lastName: document.getElementById("textbox_lastName").value
            }
            axios.post(`${searchControllerURL}/2.1`, nameAndLastNameDTO)
            .then((resp) => {
                setCandidateResults(resp.data.searchHits);
                setSuccessMessage("Successfully searched");
                setErrorMessage("");
            })
            .catch(() => {
              setErrorMessage("Something went wrong during the search by: Name and LastName");
              setSuccessMessage("");
            })
            break;
          case "2":
            let educationDTO = {
              education: document.getElementById("textbox_education").value
            }
            axios.post(`${searchControllerURL}/2.2`, educationDTO)
            .then((resp) => {
                setCandidateResults(resp.data.searchHits);
                setSuccessMessage("Successfully searched");
                setErrorMessage("");
            })
            .catch(() => {
              setErrorMessage("Something went wrong during the search by: Education");
              setSuccessMessage("");
            })        
            break;
          case "3":
            let contentData = { text: document.getElementById("textbox_sadrzaj").value };
            axios.post(`${searchControllerURL}/2.3`, contentData)
            .then((resp) => {
                setCandidateResults(resp.data.searchHits);
                setSuccessMessage("Successfully searched");
                setErrorMessage("");
            })
            .catch(() => {
              setErrorMessage("Something went wrong during the search by: CV Content");
              setSuccessMessage("");
            })   
            break;
          case "4":
            let complexQuery = { 
                firstName: document.getElementById("textbox_name").value,
                lastName: document.getElementById("textbox_lastName").value,
                education: document.getElementById("textbox_education").value,
                content: document.getElementById("textbox_sadrzaj").value,
                operator1: document.getElementById("textbox_operator1").value,
                operator2: document.getElementById("textbox_operator2").value,
            };
            axios.post(`${searchControllerURL}/2.4`, complexQuery)
            .then((resp) => {
                setCandidateResults(resp.data.jsonObject.hits.hits);
                setSuccessMessage("Successfully searched");
                setErrorMessage("");
            })
            .catch(() => {
              setErrorMessage("Something went wrong during the search by: Boolean Complex Query");
              setSuccessMessage("");
            })  
            break;
          case "5":
            let complexQuery2 = { 
              firstName: document.getElementById("textbox_name").value,
              lastName: document.getElementById("textbox_lastName").value,
              education: document.getElementById("textbox_education").value,
              content: document.getElementById("textbox_sadrzaj").value,
              operator1: document.getElementById("textbox_operator1").value,
              operator2: document.getElementById("textbox_operator2").value,
            };
            
            axios.post(`${searchControllerURL}/2.5`, complexQuery2)
            .then((resp) => {
                setCandidateResults(resp.data.jsonObject.hits.hits);
                setSuccessMessage("Successfully searched");
                setErrorMessage("");
            })
            .catch(() => {
              setErrorMessage("Something went wrong during the search by: Phrase query");
              setSuccessMessage("");
            })  
            break;
          case "6":
            let geoLocation = { 
              longitude: document.getElementById("textbox_lon").value,
              latitude: document.getElementById("textbox_lat").value,
              radius: Math.ceil(document.getElementById("textbox_radius").value)
            };
            axios.post(`${searchControllerURL}/2.6`, geoLocation)
            .then((resp) => {
                setCandidateResults(resp.data.jsonObject.hits.hits);
                setSuccessMessage("Successfully searched");
                setErrorMessage("");
            })
            .catch(() => {
              setErrorMessage("Something went wrong during the search by: Phrase query");
              setSuccessMessage("");
            })  
            
            break;
    
          default:
            console.log("Something went wrong")
            break;
        }
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
                  {/* This form is used for: 2.2 Pretraživanje aplikanata po obrazovanju*/}
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
            case "3":
                return (
                <div className='row' >
                {/* This form is used for: 2.1 Pretraživanje aplikanata po sadrzaju CV-a */}
                    <div className='container'>
                        <div className='d-flex my-2'>
                            <div className='col-6'>
                            <h5 className='mt-2'>Sadržaj CV-a:</h5>
                            </div>
                            <div className='col-6'>
                            <input id='textbox_sadrzaj' type="text" className='form-control'></input>
                            </div>
                        </div>
                    </div>
                </div>
            );
            case "4":
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
                        <div className='d-flex my-2'>
                            <div className='col-6'>
                            <h5 className='mt-2'>Operator 1:</h5>
                            </div>
                            <div className='col-6'>
                                <select id='textbox_operator1' className='form-control'>
                                    <option value="AND">AND</option> 
                                    <option value="OR">OR</option>
                                </select>
                            </div>
                        </div>
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
                        <div className='d-flex my-2'>
                            <div className='col-6'>
                            <h5 className='mt-2'>Operator 2:</h5>
                            </div>
                            <div className='col-6'>
                                <select id='textbox_operator2' className='form-control'>
                                    <option value="AND">AND</option> 
                                    <option value="OR">OR</option>
                                </select>
                            </div>
                        </div>
                        <div className='d-flex my-2'>
                            <div className='col-6'>
                            <h5 className='mt-2'>Sadržaj CV-a:</h5>
                            </div>
                            <div className='col-6'>
                            <input id='textbox_sadrzaj' type="text" className='form-control'></input>
                            </div>
                        </div>
                    </div>
                </div>
                );
                case "5":
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
                          <div className='d-flex my-2'>
                              <div className='col-6'>
                              <h5 className='mt-2'>Operator 1:</h5>
                              </div>
                              <div className='col-6'>
                                  <select id='textbox_operator1' className='form-control'>
                                      <option value="AND">AND</option> 
                                      <option value="OR">OR</option>
                                  </select>
                              </div>
                          </div>
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
                          <div className='d-flex my-2'>
                              <div className='col-6'>
                              <h5 className='mt-2'>Operator 2:</h5>
                              </div>
                              <div className='col-6'>
                                  <select id='textbox_operator2' className='form-control'>
                                      <option value="AND">AND</option> 
                                      <option value="OR">OR</option>
                                  </select>
                              </div>
                          </div>
                          <div className='d-flex my-2'>
                              <div className='col-6'>
                              <h5 className='mt-2'>Sadržaj CV-a:</h5>
                              </div>
                              <div className='col-6'>
                              <input id='textbox_sadrzaj' type="text" className='form-control'></input>
                              </div>
                          </div>
                      </div>
                  </div>
                  );
                case "6":
                  return (
                  <div className='row' >
                      {/* This form is used for: 2.1 Pretraživanje aplikanata po imenu i prezimenu */}
                      <div className='container'>
                          <div className='d-flex my-2'>
                              <div className='col-3'>
                              <h5 className='mt-2'>Grad:</h5>
                              </div>
                              <div className='col-3'>
                              <input id='textbox_city' type="text" className='form-control mx-2' ></input>
                              </div>
                              <div className='col-6'>
                                <center>
                                  <button className='form-control mx-2 bg-warning' style={{width:`90%`}} onClick={geocoding}>Get Coordinates</button>
                                </center>
                              </div>
                          </div>
                          <div className='d-flex my-2'>
                              <div className='col-3'>
                              <h5 className='mt-2'>Lon:</h5>
                              </div>
                              <div className='col-3'>
                              <input id='textbox_lon' type="text" className='form-control' readOnly></input>
                              </div>
                              <div className='col-3'>
                              <h5 className='mt-2'>Lat:</h5>
                              </div>
                              <div className='col-3'>
                              <input id='textbox_lat' type="text" className='form-control' readOnly></input>
                              </div>
                          </div>
                          <div className='d-flex my-2'>
                              <div className='col-6'>
                              <h5 className='mt-2'>Poluprečnik(udaljenost)[km]:</h5>
                              </div>
                              <div className='col-6'>
                              <input id='textbox_radius' type="number" min={1} className='form-control'></input>
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
                <ResultTable/>
                );
            case "2":
                return (
                <ResultTable/>
                );
            case "3":
                return (
                  <div>
                    <ResultTable/>
                    <ResultHighLight/>
                  </div>
                );
            case "4":
                return (
                  <div>
                    <ResultTableComplex/>
                    <ResultHighLightComplex/>
                  </div>
                );
            case "5":
                return (
                  <div>
                    <ResultTableComplex/>
                    <ResultHighLightComplex/>
                  </div>
                );
            case "6":
              return (
              <ResultTable/>
              );
        
          default:
            return (<div>{selectedValue}</div>);
        }
      }


  return (
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
                <option value="6">2.6 Pretraživanje po Geolokaciji</option>
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
                <div className='bg-danger text-white my-3' style={{width:"100px"}}>{errorMessage}</div>
                <div className='bg-success text-white my-3' style={{width:"100px"}}>{successMessage}</div>
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
  );
};

export default SearchPage;
