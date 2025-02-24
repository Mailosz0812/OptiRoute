declare const L: any;

interface Address {
    address: string;
    name: string;
    lat: number;
    lon: number;
}

const searching: HTMLInputElement = document.querySelector("#search-input");
const addMarkerForm: HTMLFormElement = document.querySelector("#addMarker-form");
const fileImportForm: HTMLFormElement = document.querySelector("#file-import-form");

const map = L.map('map').setView([51.112456681864735, 17.035963623937587], 13);
const markers = L.markerClusterGroup();
let points: any = [];
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; OpenStreetMap contributors'
}).addTo(map);


fileImportForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const fileInput: HTMLInputElement = document.querySelector("#companiesJSON");
    const files: FileList = fileInput.files;
    if(!files || files.length === 0) return;

    const file = files[0];

    const formData = new FormData();
    formData.append('file',file);
    try{
        const response = await fetch('http://localhost:8080/uploadJSON',{
            method: 'POST',
            body: formData
        })
        if(!response.ok){
            console.log(response.status);
            throw new Error(`Http error${response.status}`);
        }
        console.log('Success');
        await getAllPoints();
    }catch (error) {
        console.log('Error ',error);
    }
});


addMarkerForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const nameInput: HTMLInputElement = document.querySelector("#compName");
    const addressInput: HTMLInputElement = document.querySelector("#compAddress");
    const latitudeInput: HTMLInputElement = document.querySelector("#Latitude");
    const longitudeInput: HTMLInputElement = document.querySelector("#Longitude");

    const postAddressData: Address = {
        address: addressInput.value,
        name: nameInput.value,
        lat: latitudeInput.valueAsNumber,
        lon: longitudeInput.valueAsNumber
    };
    try {
        const response = await fetch('http://localhost:8080/addAddress', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(postAddressData),
        });
        if (!response.ok) {
            throw new Error(`HTTP error: ${response.status}`);
        }

        const responseData: Address = await response.json();
        console.log(responseData.lon,responseData.lat,responseData.address,responseData.name);
        const marker = L.marker([responseData.lat,responseData.lon]);
        marker.bindPopup(`<b>${responseData.name}</b><br>${responseData.address}`);
        markers.addLayer(marker);
        points.push(marker);
        map.addLayer(markers);
        map.setView([responseData.lat, responseData.lon], 19);
        marker.openPopup();
    }catch (Error){
        console.log('Error: ',Error)
    }
    addMarkerForm.reset();

});

function clearMap() {
    map.eachLayer((layer) => {
        if (!(layer instanceof L.TileLayer)) {
            map.removeLayer(layer);
        }
    });
}

async function getAllPoints() {
    try {
        const response = await fetch('http://localhost:8080/getAllPoints');

        if (!response.ok) throw new Error(`HTTP ${response.status}`);
        const data = await response.json();
        const companies: Address[] = Object.values(data);
        clearMap();
        companies.forEach(company => {
            const marker = L.marker([company.lat, company.lon])
                .bindPopup(`<b>${company.name}</b><br>${company.address}`);
            markers.addLayer(marker);
            points.push(marker);
        });
        map.addLayer(markers);

    } catch (error) {
        console.error('Initial load error:', error);
    }
}

document.addEventListener('DOMContentLoaded', async () => {
    await getAllPoints();
});

searching.addEventListener("keydown", async (event) => {
    if(event.key === "Enter") {
        event.preventDefault();
        const searchTerm = encodeURIComponent(searching.value);
        try {
            const company = await getPoint(`http://localhost:8080/findByName?name=${searchTerm}`);
            points.forEach(point => {
                const pointLatLng = point.getLatLng();
                if (company.lat === pointLatLng.lat && company.lon === pointLatLng.lng) {
                    point.openPopup();
                }
            });
            map.setView([company.lat, company.lon], 19);
        } catch (error) {
            console.error('Search error:', error);
        }
    }
});

async function getPoint(url: string): Promise<Address> {
    const response = await fetch(url);
    if(!response.ok) throw new Error(`HTTP ${response.status}`);
    return response.json();
}