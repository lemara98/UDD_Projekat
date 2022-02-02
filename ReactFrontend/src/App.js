import './App.css';
import { useState } from 'react';
import { useEffect } from 'react';


function App() {
  const [selectedValue, setSelectedValue] = useState(``);

  useEffect(() => {

  });
  


  function changeSelectedValue(event) {
    setSelectedValue(event.target.value.toString());
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
            {selectedValue === `1` ?
              <div className='row' >
                {/* This form is used for: 2.1 Pretraživanje aplikanata po imenu i prezimenu */}
                <div className='container'>
                  <div className='d-flex my-2'>
                    <div className='col-6'>
                      <h5 className='mt-2'>Ime:</h5>
                    </div>
                    <div className='col-6'>
                      <input type="text" className='form-control'></input>
                    </div>
                  </div>
                  <div className='d-flex my-2'>
                    <div className='col-6'>
                      <h5 className='mt-2'>Prezime:</h5>
                    </div>
                    <div className='col-6'>
                      <input type="text" className='form-control'></input>
                    </div>
                  </div>
                </div>
              </div>
              :
              selectedValue
            }
            {/* Button Pretrazi part */}
            <div className='row mt-2'>
              <div className='col-5'></div>
              <div className='col-5'><button className='form-control' style={{width:"100px"}}>Pretraži</button></div>
              <div className='col-5'></div>
              
            </div>
          </div>
          <div className='col-6'>
            
          </div>
        </div>
      </div>
      
    </div>
  );
}

export default App;
