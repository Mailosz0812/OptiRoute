var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __generator = (this && this.__generator) || function (thisArg, body) {
    var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g = Object.create((typeof Iterator === "function" ? Iterator : Object).prototype);
    return g.next = verb(0), g["throw"] = verb(1), g["return"] = verb(2), typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
    function verb(n) { return function (v) { return step([n, v]); }; }
    function step(op) {
        if (f) throw new TypeError("Generator is already executing.");
        while (g && (g = 0, op[0] && (_ = 0)), _) try {
            if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
            if (y = 0, t) op = [op[0] & 2, t.value];
            switch (op[0]) {
                case 0: case 1: t = op; break;
                case 4: _.label++; return { value: op[1], done: false };
                case 5: _.label++; y = op[1]; op = [0]; continue;
                case 7: op = _.ops.pop(); _.trys.pop(); continue;
                default:
                    if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                    if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                    if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                    if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                    if (t[2]) _.ops.pop();
                    _.trys.pop(); continue;
            }
            op = body.call(thisArg, _);
        } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
        if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
    }
};
var _this = this;
var chooseMarkerForm = document.querySelector("#chooseMarker-form");
var companyName = document.querySelector("#compName");
var companyAddress = document.querySelector("#compAddress");
var compLat = document.querySelector("#Latitude");
var compLon = document.querySelector("#Longitude");
var chooseButton = document.querySelector("#chooseComp-button");
var findRouteButton = document.querySelector("#findRoute-Button");
var bikeButton = document.querySelector("#bike-Button");
var carButton = document.querySelector("#car-Button");
var clearMapButton = document.querySelector("#clearMap-Button");
var addressArray = [];
var vehicleType = "";
function addMarkerToMap(compName, compLat, compLon, compAddress) {
    var marker = L.marker([compLat, compLon]);
    marker.bindPopup("<b>".concat(compName, "</b><br>").concat(compAddress));
    map.addLayer(marker);
}
var isFetching = false;
chooseButton.addEventListener("click", function (event) { return __awaiter(_this, void 0, void 0, function () {
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                event.preventDefault();
                if (isFetching)
                    return [2 /*return*/];
                isFetching = true;
                return [4 /*yield*/, getAllPoints()];
            case 1:
                _a.sent();
                return [2 /*return*/];
        }
    });
}); });
chooseMarkerForm.addEventListener("submit", function (event) { return __awaiter(_this, void 0, void 0, function () {
    var address, name, lat, lon, userAddress;
    return __generator(this, function (_a) {
        event.preventDefault();
        address = companyAddress.value;
        name = companyName.value;
        lat = compLat.valueAsNumber;
        lon = compLon.valueAsNumber;
        chooseMarkerForm.reset();
        if (addressArray.length == 2) {
            alert("You can only add 2 addresses");
            return [2 /*return*/];
        }
        userAddress = {
            address: address,
            name: name,
            lat: lat,
            lon: lon
        };
        addressArray.push(userAddress);
        addMarkerToMap(name, lat, lon, address);
        return [2 /*return*/];
    });
}); });
carButton.addEventListener("click", function () {
    bikeButton.style.background = "#0B64AD";
    carButton.style.background = "#2A8D87";
    vehicleType = "car";
});
bikeButton.addEventListener("click", function () {
    carButton.style.background = "#0B64AD";
    bikeButton.style.background = "#2A8D87";
    vehicleType = "bike";
});
findRouteButton.addEventListener("click", function (event) { return __awaiter(_this, void 0, void 0, function () {
    var requestBody, response, responseData, routingResponse, routeCoordinates, Error_1;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                event.preventDefault();
                if (vehicleType.length === 0) {
                    return [2 /*return*/];
                }
                if (addressArray.length !== 2) {
                    return [2 /*return*/];
                }
                requestBody = {
                    type: 'route',
                    vehicle: vehicleType,
                    startAddress: addressArray[0],
                    finishAddress: addressArray[1]
                };
                _a.label = 1;
            case 1:
                _a.trys.push([1, 4, , 5]);
                return [4 /*yield*/, fetch('http://localhost:8080/routing', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(requestBody)
                    })];
            case 2:
                response = _a.sent();
                if (!response.ok) {
                    console.log(response.body);
                    throw new Error("Http error ".concat(response.status));
                }
                return [4 /*yield*/, response.json()];
            case 3:
                responseData = _a.sent();
                routingResponse = responseData.routingResponse;
                routeCoordinates = routingResponse.routes[0].geometry.coordinates.map(function (cord) { return [cord[1], cord[0]]; });
                L.polyline(routeCoordinates, {
                    color: 'blue',
                    weight: 5
                }).addTo(map);
                return [3 /*break*/, 5];
            case 4:
                Error_1 = _a.sent();
                console.log('Error:', Error_1);
                return [3 /*break*/, 5];
            case 5: return [2 /*return*/];
        }
    });
}); });
clearMapButton.addEventListener("click", function (event) {
    event.preventDefault();
    clearMap();
    addressArray = [];
    bikeButton.style.background = "#0B64AD";
    carButton.style.background = "#0B64AD";
    vehicleType = "";
    isFetching = false;
});
