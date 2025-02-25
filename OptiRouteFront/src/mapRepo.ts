declare const L: any;

interface Address {
    address: string;
    name: string;
    lat: number;
    lon: number;
}
const map = L.map('map').setView([51.877751556071914, 18.779998184004356], 7);
const markers = L.markerClusterGroup();
let points: any = [];
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; OpenStreetMap contributors'
}).addTo(map);

async function getAllPoints() {
    try {
        clearMap();

        const response = await fetch('http://localhost:8080/getAllPoints');

        if (!response.ok) throw new Error(`HTTP ${response.status}`);
        const data = await response.json();
        const companies: Address[] = Object.values(data);

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


function clearMap() {
    map.eachLayer((layer) => {
        if (!(layer instanceof L.TileLayer)) {
            map.removeLayer(layer);
        }
    });
    markers.clearLayers();

    points = [];
}