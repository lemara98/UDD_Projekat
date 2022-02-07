import React, { useState } from 'react';
import axios from 'axios';

const UploadPage = () => {
    const uploadControllerURL = "http://localhost:8080/api/upload";

    const [errorMessage, setErrorMessage] = useState("");
    

    function uploadPDF() {
        let firstname = document.getElementById("textbox_ime").value;
        let lastname = document.getElementById("textbox_prezime").value;
        let dateOfBirth = document.getElementById("textbox_datum").value;
        let city = document.getElementById("textbox_grad").value;
        let phoneNumber = document.getElementById("textbox_telefon").value;
        let education = document.getElementById("textbox_obrazovanje").value;
        let workingExperience = document.getElementById("textbox_iskustvo").value;
        let cv = document.getElementById("upload_pdf_file").files[0];

        var geoPointLon = null;
        var geoPointLat = null;
        navigator.geolocation.getCurrentPosition(function(position) {
            geoPointLat = position.coords.latitude;
            geoPointLon = position.coords.longitude;

            let formData = new FormData();
            formData.append('firstname', firstname);
            formData.append('lastname', lastname);
            formData.append('dateOfBirth', dateOfBirth);
            formData.append('city', city);
            formData.append('phoneNumber', phoneNumber);
            formData.append('education', education);
            formData.append('workingExperience', workingExperience);
            formData.append('cvDoc', cv);
            formData.append('geoPointLat', geoPointLat);
            formData.append('geoPointLon', geoPointLon);

            axios.post(`${uploadControllerURL}/cv`, formData)
            .then(() => {
                setErrorMessage("");
            })
            .catch(() => {
                setErrorMessage("Upload CV PDF failed!");
            })
        });
    }
        
        
    
        
    
        


  return (
    <div className='row my-3'>
        <div className='col-1'></div>
        <div className='col-10 container'>
            <div className='row d-flex my-2'>
                <div className="col-6">
                    <h4 className='me-3'>Ime: </h4>
                </div>
                <div className="col-6">
                    <input id='textbox_ime' type="text" className='form-control'></input>
                </div>
            </div>
            <div className='row d-flex my-2'>
                <div className="col-6">
                    <h4 className='me-3'>Prezime: </h4>
                </div>
                <div className="col-6">
                    <input id='textbox_prezime' type="text" className='form-control'></input>
                </div>
            </div>
            <div className='row d-flex my-2'>
                <div className="col-6">
                    <h4 className='me-3'>Datum rodjenja: </h4>
                </div>
                <div className="col-6">
                    <input id='textbox_datum' type="date" className='form-control'></input>
                </div>
            </div>
            <div className='row d-flex my-2'>
                <div className="col-6">
                    <h4 className='me-3'>Grad: </h4>
                </div>
                <div className="col-6">
                    <input id='textbox_grad' type="text" className='form-control'></input>
                </div>
            </div>
            <div className='row d-flex my-2'>
                <div className="col-6">
                    <h4 className='me-3'>Kontakt telefon: </h4>
                </div>
                <div className="col-6">
                    <input id='textbox_telefon' type="text" className='form-control'></input>
                </div>
            </div>
            <div className='row d-flex my-2'>
                <div className="col-6">
                    <h4 className='me-3'>Obrazovanje: </h4>
                </div>
                <div className="col-6">
                    <select id='textbox_obrazovanje' className='form-control'>
                        <option value="Osnovno">Osnovno</option> 
                        <option value="Srednje">Srednje</option>
                        <option value="Vise">Više</option>
                        <option value="Visoko">Visoko</option>
                        <option value="Specijalisticko">Specijalisticko</option>
                    </select>
                </div>
            </div>
            <div className='row d-flex my-2'>
                <div className="col-6">
                    <h4 className='me-3'>Radno iskustvo (broj godina [0-35]): </h4>
                </div>
                <div className="col-6">
                    <input id='textbox_iskustvo' type="number" min={0} max={35} className='form-control'></input>
                </div>
            </div>
            <div className='row d-flex my-2'>
                <div className="col-6">
                    <h4 className='me-3'>CV: </h4>
                </div>
                <div className="col-6">
                <input id='upload_pdf_file' type="file" className='form-control' accept="application/pdf"></input>
                </div>
            </div>
            <div className='col mt-5'>
                <center>
                    <button type='submit' style={{width: "300px"}} onClick={uploadPDF} className='form-control'>Upload</button>
                    <div className='bg-danger text-white my-3'>{errorMessage}</div>
                </center>
            </div>
        </div>
        <div className='col-1'></div>
    </div>
  );
};

export default UploadPage;
