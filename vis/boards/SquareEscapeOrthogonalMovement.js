export const xmlString = `<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<escapeGameInitializer>
	<coordinateType>SQUARE</coordinateType>
    <!-- Board items -->
    <xMax>20</xMax>
    <yMax>20</yMax>
    <locationInitializers> <!-- An array of these, 0 or more -->
        <x>5</x>
        <y>3</y>
        <locationType>BLOCK</locationType>
    </locationInitializers>
    <locationInitializers>
        <x>5</x>
        <y>2</y>
        <locationType>BLOCK</locationType>
    </locationInitializers>
        <locationInitializers>
        <x>5</x>
        <y>1</y>
        <locationType>BLOCK</locationType>
    </locationInitializers>
    <locationInitializers>
        <x>8</x>
        <y>5</y>
        <locationType>BLOCK</locationType>
    </locationInitializers>
    <locationInitializers>
        <x>3</x>
        <y>1</y>
        <locationType>CLEAR</locationType>
        <player>PLAYER1</player>
        <pieceName>FOX</pieceName>
    </locationInitializers>
    <!-- Piece items, an array of pieceTypes, 1 or more -->
    <pieceTypes>
        <movementPattern>ORTHOGONAL</movementPattern>
        <pieceName>FOX</pieceName>
        <attributes>
            <id>DISTANCE</id>
            <attrType>INTEGER</attrType>
            <intValue>5</intValue>
        </attributes>
    </pieceTypes>
</escapeGameInitializer>`;
