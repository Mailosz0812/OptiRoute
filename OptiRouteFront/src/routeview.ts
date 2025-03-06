interface routingResponse {
    code: string;
    routes: Route[];
    waypoints: Waypoint[];
}
interface Route {
    geometry: Geometry;
    legs: Leg[];
    weight_name: string;
    weight: number;
    duration: number;
    distance: number;
}
interface Geometry {
    coordinates: [number, number][];
    type: string;
}
interface Leg {
    steps: any[];
    summary: string;
    weight: number;
    duration: number;
    distance: number;
}
interface Waypoint {
    hint: string;
    distance: number;
    name: string;
    location: [number, number];
}
const chooseMarkerForm: HTMLFormElement = document.querySelector("#chooseMarker-form");
const companyName: HTMLInputElement = document.querySelector("#compName");
const companyAddress: HTMLInputElement = document.querySelector("#compAddress");
const compLat: HTMLInputElement = document.querySelector("#Latitude");
const compLon: HTMLInputElement = document.querySelector("#Longitude");
const chooseButton: HTMLButtonElement = document.querySelector("#chooseComp-button");
const findRouteButton: HTMLButtonElement = document.querySelector("#findRoute-Button");
const bikeButton: HTMLButtonElement = document.querySelector("#bike-Button");
const carButton: HTMLButtonElement = document.querySelector("#car-Button");
const clearMapButton: HTMLButtonElement = document.querySelector("#clearMap-Button");
let addressArray: Address[] = [];
let vehicleType: string = "";


function addMarkerToMap(compName:string, compLat:number,compLon:number,compAddress: string): void{
    let marker = L.marker([compLat,compLon]);
    marker.bindPopup(`<b>${compName}</b><br>${compAddress}`);
    map.addLayer(marker);
}

let isFetching = false;
chooseButton.addEventListener("click", async (event) => {
    event.preventDefault();
    if (isFetching) return;

    isFetching = true;
    await getAllPoints();

});

chooseMarkerForm.addEventListener("submit", async (event) =>{
   event.preventDefault();
    let address = companyAddress.value;
    let name = companyName.value;
    let lat = compLat.valueAsNumber;
    let lon = compLon.valueAsNumber;
    chooseMarkerForm.reset();
    if(addressArray.length == 2){
        alert("You can only add 2 addresses");
        return;
    }
    let userAddress: Address = {
      address: address,
      name: name,
      lat: lat,
      lon: lon
    };
    addressArray.push(userAddress)
   addMarkerToMap(name,lat,lon,address);
});
carButton.addEventListener("click", () => {
    bikeButton.style.background = "#0B64AD";
    carButton.style.background = "#2A8D87";
   vehicleType = "car";
});
bikeButton.addEventListener("click", () => {
    carButton.style.background = "#0B64AD";
    bikeButton.style.background = "#2A8D87";
   vehicleType = "bike"
});
findRouteButton.addEventListener("click" ,async (event) => {
    event.preventDefault();

    if(vehicleType.length === 0){
        return;
    }
    if(addressArray.length !== 2){
        return
    }
    const requestBody = {
        type: 'route',
        vehicle: vehicleType,
        startAddress: addressArray[0],
        finishAddress: addressArray[1]
    };
    try {
        const response = await fetch('http://localhost:8080/routing', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestBody)
        });
        if(!response.ok){
            console.log(response.body);
            throw new Error(`Http error ${response.status}`);
        }
        const responseData = await response.json();
        const routingResponse: routingResponse = responseData.routingResponse;
        const routeCoordinates = routingResponse.routes[0].geometry.coordinates.map(cord => [cord[1], cord[0]]);
        L.polyline(routeCoordinates, {
            color: 'blue',
            weight: 5
        }).addTo(map);
    }catch (Error){
        console.log('Error:', Error)
    }
});

clearMapButton.addEventListener("click", (event) => {
    event.preventDefault();
    clearMap();
    addressArray = [];
    bikeButton.style.background = "#0B64AD";
    carButton.style.background = "#0B64AD";
    vehicleType = "";
    isFetching = false;
})