// import { xmlString } from './SampleEscapeSquare.js';
import { xmlString } from './SampleEscapeOrtho.js';
// import { xmlString } from "./SampleEscapeHex.js";
import Honeycomb from "./honeycomb.js";
const { Grid, defineHex, rectangle } = Honeycomb;
let isHex;

function parseXML(xml) {
  const parser = new DOMParser();
  const xmlDoc = parser.parseFromString(xml, "text/xml");

  const coordinateType = xmlDoc.querySelector("coordinateType")?.textContent;

  let xMax, yMax;
  if (coordinateType === "HEX") {
    isHex = true;
    xMax = 25;
    yMax = 25;
  } else {
    isHex = false;
    xMax = parseInt(xmlDoc.querySelector("xMax").textContent);
    yMax = parseInt(xmlDoc.querySelector("yMax").textContent);
  }

  // <locationInitializers> tags individually wrap all board
  // data (players, walls, & pieces)
  const locationElements = xmlDoc.querySelectorAll("locationInitializers");

  // Collect board data into array
  const boardData = [];
  locationElements.forEach((loc) => {
    const x = parseInt(loc.querySelector("x").textContent);
    const y = parseInt(loc.querySelector("y").textContent);
    const locationType = loc.querySelector("locationType").textContent;
    const player = loc.querySelector("player")?.textContent || null;
    const pieceName = loc.querySelector("pieceName")?.textContent || null;

    boardData.push({ x, y, locationType, player, pieceName });
  });

  return { coordinateType, xMax, yMax, boardData };
}

function createBoard(data) {
  const { xMax, yMax, boardData } = data;

  // Create board container
  const container = document.getElementById("board-container");
  const board = document.createElement("div");

  board.className = "board";
  board.style.gridTemplateColumns = `repeat(${xMax}, 40px)`;

  // Initialize empty board
  const boardState = Array(yMax)
    .fill()
    .map(() =>
      Array(xMax)
        .fill()
        .map(() => ({
          locationType: "CLEAR",
          player: null,
          pieceName: null,
        }))
    );

  // Apply boardData from XML
  boardData.forEach((loc) => {
    // Convert to 0-based indexing for array
    const x = loc.x - 1;
    const y = loc.y - 1;

    if (x >= 0 && x < xMax && y >= 0 && y < yMax) {
      boardState[y][x] = {
        locationType: loc.locationType,
        player: loc.player,
        pieceName: loc.pieceName,
      };
    }
  });

  // Create cells
  for (let y = 0; y < yMax; y++) {
    for (let x = 0; x < xMax; x++) {
      const cell = document.createElement("div");
      cell.className = "cell";

      const cellData = boardState[y][x];

      // Add base class
      cell.classList.add(cellData.locationType.toLowerCase());

      // Add player class if present
      if (cellData.player) {
        cell.classList.add(cellData.player.toLowerCase());
      }

      // Add piece abbreviation
      if (cellData.pieceName) {
        const abbrev = getPieceAbbreviation(cellData.pieceName);
        cell.textContent = abbrev;
        cell.title = `${cellData.pieceName} (${cellData.player})`;
      } else if (cellData.locationType === "BLOCK") {
        cell.textContent = "█";
      }

      // Add coordinates as tooltip
      cell.title += `\nCoordinates: (${x + 1}, ${y + 1})`;

      board.appendChild(cell);
    }
  }

  container.appendChild(board);
  createLegend();
}

function createHexBoard(data) {
  const { boardData } = data;

  // Should dynamically set based on max/max of pieces (hardcode for now)
  const bounds = {
    minX: 0,
    minY: 0,
    maxX: 30,
    maxY: 20,
  };

  // Create hex factory to pass to Grid instance
  const Hex = defineHex({
    dimensions: 15,        // Size of drawn hex
    orientation: "pointy", // top of hex
    origin: "topLeft",     // default, sets (q,r) to topLeft
  });

  // Create a map so we can set cell data easily
  const dataMap = new Map();
  boardData.forEach((loc) => {
    dataMap.set(`${loc.x},${loc.y}`, loc);
  });

  // Calculate grid dimensions
  const width = bounds.maxX - bounds.minX + 1;
  const height = bounds.maxY - bounds.minY + 1;

  // Create grid
  const grid = new Grid(Hex, rectangle({ width, height }));
  const gridHeight = grid.pixelHeight;

  // Create container
  const container = document.getElementById("board-container");
  container.innerHTML = ""; // Clear previous
  container.style.position = "relative";

  // Render each hex
  grid.forEach((hex) => {
    // Adjust coordinates to make (0,0) or (q,r) closer to
    // center of grid (instead of topLeft)
    const adjustedX = hex.q - 10;
    const adjustedY = hex.r;

    const cellData = dataMap.get(`${adjustedX},${adjustedY}`) || {
      locationType: "EMPTY",
      player: null,
      pieceName: null,
    };

    createHexCell(hex, cellData, container, adjustedX, adjustedY);
  });

  // Set height for board
  const boardContainer = document.getElementById("board-container");
  boardContainer.style.height = `${gridHeight}px`;

  createLegend();
}

function createHexCell(hex, cellData, container, x, y) {
  const cell = document.createElement("div");
  cell.className = "hex-cell";

  // Get pixel coordinates directly
  const pixelX = hex.x;
  const pixelY = hex.y;

  // Position the cell relative to the top-left of the container
  cell.style.position = "absolute";

  // The x and y properties are the center point.
  // Subtract half the width/height to position the div's top-left corner.
  cell.style.left = `${pixelX - hex.width / 2}px`;
  cell.style.top = `${pixelY - hex.height / 2}px`;
  cell.style.width = `${hex.width}px`;
  cell.style.height = `${hex.height}px`;

  // Add base class
  cell.classList.add(cellData.locationType.toLowerCase());

  // Add player class if present
  if (cellData.player) {
    cell.classList.add(cellData.player.toLowerCase());
  }

  // Add piece abbreviation
  if (cellData.pieceName) {
    const abbrev = getPieceAbbreviation(cellData.pieceName);
    cell.textContent = abbrev;
    cell.title = `${cellData.pieceName} (${cellData.player})`;
    console.log("SET ", cellData);
  } else if (cellData.locationType === "BLOCK") {
    cell.textContent = "█";
    console.log("SET BLOCK");
  }

  // Add coordinates as tooltip
  cell.title += `\nCoordinates: (${hex.q - 10}, ${hex.r})`;

  container.appendChild(cell);
}

function getPieceAbbreviation(pieceName) {
  const abbreviations = {
    HORSE: "H",
    FROG: "F",
    HUMMINGBIRD: "B",
    FOX: "X",
  };
  return abbreviations[pieceName] || pieceName.charAt(0);
}

function createLegend() {
  const legend = document.getElementById("legend");
  const items = [
    { color: "#f0f0f0", label: "Clear (empty)" },
    { color: "#666", label: "Blocked" },
    { color: "#aaccff", label: "Player 1" },
    { color: "#ffaaaa", label: "Player 2" },
    { label: "H = Horse", symbol: "H" },
    { label: "F = Frog", symbol: "F" },
    { label: "B = Hummingbird", symbol: "B" },
    { label: "X = Fox", symbol: "X" },
  ];

  items.forEach((item) => {
    const div = document.createElement("div");
    div.className = "legend-item";

    if (item.color) {
      const colorBox = document.createElement("div");
      colorBox.className = "legend-color";
      colorBox.style.backgroundColor = item.color;
      div.appendChild(colorBox);
    }

    const label = document.createElement("span");
    label.textContent = item.label;
    div.appendChild(label);

    legend.appendChild(div);
  });
}

// Parse and display
const boardData = parseXML(xmlString);

if (isHex) {
  createHexBoard(boardData);
} else {
  createBoard(boardData);
}
