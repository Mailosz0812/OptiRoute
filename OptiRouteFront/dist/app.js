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
var searching = document.querySelector("#search-input");
var addMarkerForm = document.querySelector("#addMarker-form");
var fileImportForm = document.querySelector("#file-import-form");
var map = L.map('map').setView([51.112456681864735, 17.035963623937587], 13);
var markers = L.markerClusterGroup();
var points = [];
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; OpenStreetMap contributors'
}).addTo(map);
fileImportForm.addEventListener("submit", function (event) { return __awaiter(_this, void 0, void 0, function () {
    var fileInput, files, file, formData, response, error_1;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                event.preventDefault();
                fileInput = document.querySelector("#companiesJSON");
                files = fileInput.files;
                if (!files || files.length === 0)
                    return [2 /*return*/];
                file = files[0];
                formData = new FormData();
                formData.append('file', file);
                _a.label = 1;
            case 1:
                _a.trys.push([1, 4, , 5]);
                return [4 /*yield*/, fetch('http://localhost:8080/uploadJSON', {
                        method: 'POST',
                        body: formData
                    })];
            case 2:
                response = _a.sent();
                if (!response.ok) {
                    console.log(response.status);
                    throw new Error("Http error".concat(response.status));
                }
                console.log('Success');
                return [4 /*yield*/, getAllPoints()];
            case 3:
                _a.sent();
                return [3 /*break*/, 5];
            case 4:
                error_1 = _a.sent();
                console.log('Error ', error_1);
                return [3 /*break*/, 5];
            case 5: return [2 /*return*/];
        }
    });
}); });
addMarkerForm.addEventListener("submit", function (event) { return __awaiter(_this, void 0, void 0, function () {
    var nameInput, addressInput, latitudeInput, longitudeInput, postAddressData, response, responseData, marker, Error_1;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                event.preventDefault();
                nameInput = document.querySelector("#compName");
                addressInput = document.querySelector("#compAddress");
                latitudeInput = document.querySelector("#Latitude");
                longitudeInput = document.querySelector("#Longitude");
                postAddressData = {
                    address: addressInput.value,
                    name: nameInput.value,
                    lat: latitudeInput.valueAsNumber,
                    lon: longitudeInput.valueAsNumber
                };
                _a.label = 1;
            case 1:
                _a.trys.push([1, 4, , 5]);
                return [4 /*yield*/, fetch('http://localhost:8080/addAddress', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(postAddressData),
                    })];
            case 2:
                response = _a.sent();
                if (!response.ok) {
                    throw new Error("HTTP error: ".concat(response.status));
                }
                return [4 /*yield*/, response.json()];
            case 3:
                responseData = _a.sent();
                console.log(responseData.lon, responseData.lat, responseData.address, responseData.name);
                marker = L.marker([responseData.lat, responseData.lon]);
                marker.bindPopup("<b>".concat(responseData.name, "</b><br>").concat(responseData.address));
                markers.addLayer(marker);
                points.push(marker);
                map.addLayer(markers);
                map.setView([responseData.lat, responseData.lon], 19);
                marker.openPopup();
                return [3 /*break*/, 5];
            case 4:
                Error_1 = _a.sent();
                console.log('Error: ', Error_1);
                return [3 /*break*/, 5];
            case 5:
                addMarkerForm.reset();
                return [2 /*return*/];
        }
    });
}); });
function clearMap() {
    map.eachLayer(function (layer) {
        if (!(layer instanceof L.TileLayer)) {
            map.removeLayer(layer);
        }
    });
}
function getAllPoints() {
    return __awaiter(this, void 0, void 0, function () {
        var response, data, companies, error_2;
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0:
                    _a.trys.push([0, 3, , 4]);
                    return [4 /*yield*/, fetch('http://localhost:8080/getAllPoints')];
                case 1:
                    response = _a.sent();
                    if (!response.ok)
                        throw new Error("HTTP ".concat(response.status));
                    return [4 /*yield*/, response.json()];
                case 2:
                    data = _a.sent();
                    companies = Object.values(data);
                    clearMap();
                    companies.forEach(function (company) {
                        var marker = L.marker([company.lat, company.lon])
                            .bindPopup("<b>".concat(company.name, "</b><br>").concat(company.address));
                        markers.addLayer(marker);
                        points.push(marker);
                    });
                    map.addLayer(markers);
                    return [3 /*break*/, 4];
                case 3:
                    error_2 = _a.sent();
                    console.error('Initial load error:', error_2);
                    return [3 /*break*/, 4];
                case 4: return [2 /*return*/];
            }
        });
    });
}
document.addEventListener('DOMContentLoaded', function () { return __awaiter(_this, void 0, void 0, function () {
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0: return [4 /*yield*/, getAllPoints()];
            case 1:
                _a.sent();
                return [2 /*return*/];
        }
    });
}); });
searching.addEventListener("keydown", function (event) { return __awaiter(_this, void 0, void 0, function () {
    var searchTerm, company_1, error_3;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                if (!(event.key === "Enter")) return [3 /*break*/, 4];
                event.preventDefault();
                searchTerm = encodeURIComponent(searching.value);
                _a.label = 1;
            case 1:
                _a.trys.push([1, 3, , 4]);
                return [4 /*yield*/, getPoint("http://localhost:8080/findByName?name=".concat(searchTerm))];
            case 2:
                company_1 = _a.sent();
                points.forEach(function (point) {
                    var pointLatLng = point.getLatLng();
                    if (company_1.lat === pointLatLng.lat && company_1.lon === pointLatLng.lng) {
                        point.openPopup();
                    }
                });
                map.setView([company_1.lat, company_1.lon], 19);
                return [3 /*break*/, 4];
            case 3:
                error_3 = _a.sent();
                console.error('Search error:', error_3);
                return [3 /*break*/, 4];
            case 4: return [2 /*return*/];
        }
    });
}); });
function getPoint(url) {
    return __awaiter(this, void 0, void 0, function () {
        var response;
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0: return [4 /*yield*/, fetch(url)];
                case 1:
                    response = _a.sent();
                    if (!response.ok)
                        throw new Error("HTTP ".concat(response.status));
                    return [2 /*return*/, response.json()];
            }
        });
    });
}
