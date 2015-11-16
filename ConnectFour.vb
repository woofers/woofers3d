Public Class ConnectFour

	Private grid As Grid

	Private Sub onStart(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles MyBase.Load
		grid = New Grid()
		grid.Location = New System.Drawing.Point(25, 25)
		Me.Controls.Add(grid)
	End Sub
End Class

Public Class Grid
	Inherits Panel

	Private optionsPanel As OptionsPanel
	Private computerTimer As Timer
	Private shapeContainer As Microsoft.VisualBasic.PowerPacks.ShapeContainer
	Public colors(1) As Color
	Public currentColor As Color
	Public isEnabled As Boolean

	Private ovals(gridSize.X, gridSize.Y) As MyOval
	Public placableLocations(gridSize.X) As Integer
	Private gridSize As Point
	Public turnsLeft As Integer

	Private Const ovalSize As Integer = 70
	Private Const spacing As Integer = ovalSize / 8
	Private Const resizeForm As Boolean = True
	Private Const computerDelay As Integer = 0.5 * 1000
	Public Const connectSize As Integer = 4

	Private Function rand(ByVal Low As Integer, ByVal High As Integer) As Integer
		Return CInt(Int((High - Low + 1) * Rnd()) + Low)
	End Function

	Public Sub New(Optional ByVal width As Integer = 6, Optional ByVal height As Integer = 5)
		'// Set Colors
		colors = { Color.Gold, Color.Red }
		currentColor = colors(0)

		'// Set Size
		gridSize = New System.Drawing.Point(width, height)

		'// Options Panel
		optionsPanel = New OptionsPanel(gridSize.X, gridSize.Y)
		With optionsPanel
			.Name = optionsPanel.ToString()
			.Location = New System.Drawing.Point((ovalSize + spacing) * (gridSize.X + 1) + 20, 0)
		End With
		Me.Controls.Add(optionsPanel)

		'// Ovals
		createOvals()

		'// Timer
		computerTimer = New Timer()
		computerTimer.Interval = computerDelay
		AddHandler computerTimer.Tick, AddressOf Me.onTimer
	End Sub

	Private Sub createOvals()
		'// Resize Array
		ReDim ovals(gridSize.X, gridSize.Y)
		ReDim placableLocations(gridSize.X)

		'// Shape Container
		shapeContainer = New Microsoft.VisualBasic.PowerPacks.ShapeContainer()
		Me.Controls.Add(shapeContainer)

		'// Create Ovals
		For x As Integer = 0 To gridSize.X
			For y As Integer = 0 To gridSize.Y
				ovals(x, y) = New MyOval
				With ovals(x, y)
					.Name = x & "," & y & ",Ovals"
					.BackColor = SystemColors.Control
					.BackStyle = PowerPacks.BackStyle.Opaque
					.Location = New System.Drawing.Point(((ovalSize + spacing) * x), (ovalSize + spacing) * y)
					.Size = New System.Drawing.Size(ovalSize, ovalSize)
					.arrayLocation.X = x
					.arrayLocation.Y = y
				End With
				shapeContainer.Shapes.Add(ovals(x, y))
				AddHandler ovals(x, y).MouseDown, AddressOf Me.ifClicked
			Next
		Next

		'// Placable Locations
		For i As Integer = 0 To gridSize.X
			placableLocations(i) = gridSize.Y
		Next

		'// Update Panel Size
		Me.Size = New System.Drawing.Size((ovalSize + spacing) * (gridSize.X + 1) + 160, (ovalSize + spacing) * (gridSize.Y + 1) + 45)
		shapeContainer.Size = Me.Size

		'// Update Form Size
		If resizeForm Then
			ConnectFour.ClientSize = Me.Size
		End If

		'// Move Options
		If Not optionsPanel Is Nothing Then
			optionsPanel.Location = New System.Drawing.Point((ovalSize + spacing) * (gridSize.X + 1) + 20, 0)
		End If

		'// Set Turns Left
		turnsLeft = (gridSize.X + 1) * (gridSize.Y + 1)
	End Sub

	Private Sub removeOvals()
		For x As Integer = 0 To gridSize.X
			For y As Integer = 0 To gridSize.Y
				shapeContainer.Shapes.Remove(ovals(x, y))
				ovals(x, y) = Nothing
			Next
		Next
	End Sub

	Public Sub resetGrid()
		computerTimer.Stop()
		resizeGrid(gridSize.X, gridSize.Y)
		optionsPanel.reset()
		isEnabled = False
	End Sub

	Public Sub resizeGrid(ByVal width As Integer, ByVal height As Integer)
		removeOvals()
		gridSize = New System.Drawing.Point(width, height)
		createOvals()
	End Sub

	Public Sub ifClicked(ByVal sender As System.Object, ByVal e As System.Windows.Forms.MouseEventArgs)
		If Not isEnabled Then
			Return
		End If
		If placableLocations(sender.arrayLocation.X) >= 0 Then
			ovals(sender.arrayLocation.X, placableLocations(sender.arrayLocation.X)).fill()
			If Not checkWin() Then
				If Not optionsPanel.isTwoPlayer Then
					computerTurn()
				End If
			End If
		End If

        Dim integerGrid As IntegerGrid
        integerGrid = toIntegerGrid()
        integerGrid.swapColors()
        integerGrid.evaluateGrid()
	End Sub

	Public Function colorToString(ByVal color As Color) As String
		Dim temp As Array
		temp = color.ToString().Split("[")
		Return temp(1).ToString.Replace("]", "")
	End Function

	Public Function colorToInt(ByVal color As Color) As Integer
		Select Case color
			Case colors(0)
				Return 1
			Case colors(1)
				Return 2
			Case Else
				Return 0
		End Select
	End Function

	Private Sub swapColors()
		currentColor = getOppositeColor()
		optionsPanel.updateColor()
	End Sub

	Private Function getOppositeColor() As Color
		If currentColor = colors(1) Then
			Return colors(0)
		Else
			Return colors(1)
		End If
	End Function

	Public Sub winMsg(ByVal typeOfWin As String)
		MsgBox(colorToString(currentColor) & " wins with a " & typeOfWin & " connection")
	End Sub

	Private Function ovalExists(ByVal x As Integer, ByVal y As Integer) As Boolean
		If x > gridSize.Y Or y > gridSize.Y Then
			Return False
		End If
		If 0 > x Or 0 > y Then
			Return False
		End If
		Return True
	End Function

	Public Sub startGame()
		If Not optionsPanel.isTwoPlayer Then
			If optionsPanel.computerBattle Then
				computerTurn()
			Else
				If optionsPanel.computerStarts Then
					computerTurn()
				End If
			End If
		End If
	End Sub

	Private Function checkWin() As Boolean
		Dim integerGrid As IntegerGrid
		integerGrid = toIntegerGrid()
		If integerGrid.checkWin() Then
			Return True
		End If
		swapColors()
		Return False
	End Function

	Public Sub computerTurn()
		isEnabled = False
		computerTimer.Start()
	End Sub

	Private Sub onTimer(ByVal sender As System.Object, ByVal e As System.EventArgs)
		computerTimer.Stop()
		If optionsPanel.isHard Then
			If optionsPanel.computerBattle And currentColor = colors(0) Then
				hardComputer(4)
				Return
			End If
			hardComputer(4)
		Else
			easyComputer()
		End If
	End Sub

	Private Sub easyComputer()
		Dim spot As Integer
		Do
			spot = rand(0, gridSize.X)
			If placableLocations(spot) >= 0 Then
				If ovalExists(spot, placableLocations(spot)) Then
					If ovals(spot, placableLocations(spot)).isEmpty() Then
						Exit Do
					End If
				End If
			End If
		Loop
		ovals(spot, placableLocations(spot)).fill()

		If checkWin() Then
			Return
		End If
		If optionsPanel.computerBattle Then
			computerTurn()
			Return
		End If
		isEnabled = True
	End Sub

	Private Sub hardComputer(ByVal depth As Integer)
		Dim integerGrid As IntegerGrid
		integerGrid = toIntegerGrid()

		Dim xSpot As Integer = integerGrid.miniMax(depth)
		ovals(xSpot, placableLocations(xSpot)).fill()

		If checkWin() Then
			Return
		End If
		If optionsPanel.computerBattle Then
			computerTurn()
			Return
		End If
		isEnabled = True
	End Sub

	Public Function clone() As Grid
		Dim newGrid As Grid
		Dim newOvals(gridSize.X, gridSize.Y) As MyOval
		Dim newPlacableLocations(gridSize.X) As Integer

		newGrid = New Grid(gridSize.X, gridSize.Y)
		newGrid.removeOvals()

		For x As Integer = 0 To gridSize.X
			For y As Integer = 0 To gridSize.Y
				newOvals(x, y) = ovals(x, y).clone(newGrid.shapeContainer)
				AddHandler newOvals(x, y).MouseDown, AddressOf newGrid.ifClicked
			Next
		Next

		For i As Integer = 0 To gridSize.X
			newPlacableLocations(i) = placableLocations(i)
		Next

		With newGrid
			.currentColor = currentColor
			.gridSize = New Point(gridSize.X, gridSize.Y)
			.ovals = newOvals
			.placableLocations = newPlacableLocations
			.turnsLeft = turnsLeft
		End With

		Return newGrid
	End Function

	Public Function toArray() As Array
		Dim newArray(gridSize.X, gridSize.Y) As Integer
		For x As Integer = 0 To gridSize.X
			For y As Integer = 0 To gridSize.Y
				newArray(x, y) = ovals(x, y).getTagColor()
			Next
		Next
		Return newArray
	End Function

	Public Function toIntegerGrid() As IntegerGrid
		Dim newIntegerGrid As IntegerGrid
		Dim newPlacableLocations(gridSize.X) As Integer

		newIntegerGrid = New IntegerGrid()

		For i As Integer = 0 To gridSize.X
			newPlacableLocations(i) = placableLocations(i)
		Next

		With newIntegerGrid
			.currentColor = colorToInt(currentColor)
			.gridSize = New Point(gridSize.X, gridSize.Y)
			.ovals = toArray()
			._parent = Me
			.placableLocations = newPlacableLocations
			.turnsLeft = turnsLeft
		End With
		Return newIntegerGrid
	End Function

End Class

Public Class IntegerGrid

	Public turnsLeft As Integer
	Public currentColor As Integer
	Public gridSize As Point
	Public ovals(gridSize.X, gridSize.Y) As Integer
	Public placableLocations(gridSize.X) As Integer
	Public _parent As Object

	Private Const infinity As Integer = Integer.MaxValue

	Public Sub undoMove(ByVal x As Integer)
		turnsLeft += 1
		placableLocations(x) += 1
		ovals(x, placableLocations(x)) = 0
	End Sub

	Private Function rowIsFull(ByVal x As Integer) As Boolean
		If x > gridSize.X Then
			Return True
		End If

		If placableLocations(x) < 0 Then
			Return True
		End If
		Return False
	End Function

	Public Sub swapColors()
		Select Case currentColor
			Case 1
				currentColor = 2
			Case Else
				currentColor = 1
		End Select
	End Sub

	Private Function getOppositeColor() As Integer
		If currentColor = 1 Then
			Return 2
		Else
			Return 1
		End If
	End Function

	Public Sub printBoard()
		Dim board As String = ""
		Dim temp As String = ""
		Debug.Print("---Board Start---")
		For y As Integer = 0 To gridSize.Y
			temp = ""
			For x As Integer = 0 To gridSize.X
				temp = temp & ovals(x, y) & ", "
			Next
			If Not y = gridSize.Y Then
				temp = temp & vbNewLine
			End If
			board = board & temp
		Next
		Debug.Print(board)
		Debug.Print("----Board End----")
	End Sub

	Public Function miniMax(ByVal depth As Integer) As Integer
		swapColors()
		If depth Mod 2 = 0 Or depth = 0 Then
			Return max(depth, depth, -infinity, infinity)
		End If
		Return min(depth, depth, -infinity, infinity)
	End Function

	Public Function max(ByVal depth As Integer, ByVal maxDepth As Integer, ByVal alpha As Integer, ByVal beta As Integer) As Integer
		Dim value As Integer
		Dim newValue As Integer
		Dim xSpot As Integer = -1

		swapColors()
		value = alpha
		For x As Integer = 0 To gridSize.X
			If Not rowIsFull(x) Then
				placeOval(x, placableLocations(x))

				If isWin() Then
					newValue = infinity - maxDepth + depth
				ElseIf depth <= 0 Or turnsLeft <= 0 Then
					newValue = evaluateGrid()
				Else
					newValue = min(depth - 1, maxDepth, alpha, beta)
					swapColors()
				End If

				undoMove(x)

				If xSpot = -1 Then
					xSpot = x
				End If
				If newValue > value Then
					value = newValue
					xSpot = x
				End If
				alpha = Math.Max(alpha, value)
				If alpha => beta
					Exit For
				End If
			End If
		Next
		If depth = maxDepth Then
			Return xSpot
		End If
		Return value
	End Function

    Public Function min(ByVal depth As Integer, ByVal maxDepth As Integer, ByVal alpha As Integer, ByVal beta As Integer) As Integer
		Dim value As Integer
		Dim newValue As Integer
		Dim xSpot As Integer = -1

		swapColors()
		value = beta
		For x As Integer = 0 To gridSize.X
			If Not rowIsFull(x) Then
				placeOval(x, placableLocations(x))

				If isWin() Then
					newValue = -infinity + maxDepth - depth
				ElseIf depth <= 0 Or turnsLeft <= 0 Then
					newValue = evaluateGrid()
				Else
					newValue = max(depth - 1, maxDepth, alpha, beta)
					swapColors()
				End If

				undoMove(x)

				If xSpot = -1 Then
					xSpot = x
				End If

				If newValue < value Then
					value = newValue
					xSpot = x
				End If
				beta = Math.Min(beta, value)
				If alpha => beta
					Exit For
				End If
			End If
		Next
		If depth = maxDepth Then
			Return xSpot
		End If
		Return value
	End Function

	Public Function evaluateGrid() As Integer
		Dim count As Integer
		Dim temp As Integer
		For x As Integer = 0 To gridSize.X
			If Not rowIsFull(x) Then
				temp = evaluateSpot(x, placableLocations(x))
				If temp = -ScoreConstants.win Then
					Return -infinity
				End If
				count += temp
			End If
		Next
		Return count
	End Function

	Public Function evaluateSpot(ByVal x As Integer, ByVal y As Integer) As Integer
		Dim topCount As Integer
		Dim bottomCount As Integer
		topCount += evaluateHorizontal(x, y)
		topCount += evaluateVertical(x, y)
		topCount += evaluateDiagonal(x, y)
		swapColors()

		bottomCount += evaluateHorizontal(x, y)
		bottomCount += evaluateVertical(x, y)
		bottomCount += evaluateDiagonal(x, y)
		swapColors()

		If bottomCount >= ScoreConstants.win Then
			Return -ScoreConstants.win
		End If
		Return topCount - bottomCount
	End Function

	Private Function evaluateHorizontal(ByVal _x As Integer, ByVal _y As Integer) As Integer
		Dim value As Integer

		value += getHorizontalEnd(currentColor, _x, _y) * ScoreConstants.notEndOfRow
		If value = 0
			Return value
		End If

		'// Evaluate Rows
		Select Case getHorizontalCount(currentColor, _x, _y)
			Case >= 3
				value += ScoreConstants.win
			Case 2
				value += ScoreConstants.rowThree
			Case 1
				value += ScoreConstants.rowTwo
		End Select

		Return value
	End Function

	Private Function getHorizontalCount(ByVal color As Integer, ByVal _x As Integer, ByVal _y As Integer) As Integer
		Dim count As Integer

		'// Evaluate Left Rows
		For x As Integer = _x - 1 To _x - 3 Step -1
			If ovalExists(x, _y) Then
				If Not ovals(x, _y) = 0 Then
					If ovals(x, _y) = color Then
						count += 1
						Continue For
					End If
				End If
			End If
			Exit For
		Next

		'// Evaluate Right Rows
		For x As Integer = _x + 1 To _x + 3
			If ovalExists(x, _y) Then
				If Not ovals(x, _y) = 0 Then
					If ovals(x, _y) = color Then
						count += 1
						Continue For
					End If
				End If
			End If
			Exit For
		Next

		Return count
	End Function

	Private Function getHorizontalEnd(ByVal color As Integer, ByVal _x As Integer, ByVal _y As Integer) As Integer
		Dim topCount As Integer
		Dim bottomCount As Integer
		Dim endOfRows As Integer

		'// Evaluate Left Rows
		For x As Integer = _x - 1 To _x - 3 Step -1
			If ovalExists(x, _y) Then
				If Not ovals(x, _y) = getOppositeColor() Then
					topCount += 1
					Continue For
				End If
			End If
			Exit For
		Next

		'// Evaluate Right Rows
		For x As Integer = _x + 1 To _x + 3
			If ovalExists(x, _y) Then
				If Not ovals(x, _y) = getOppositeColor() Then
					bottomCount += 1
					Continue For
				End If
			End If
			Exit For
		Next

        If topCount >= 3 Then
			endOfRows += 1
		End If

		If bottomCount >= 3 Then
			endOfRows += 1
		End If

		If topCount + bottomCount >= 3 And topCount < 3 And bottomCount < 3
			endOfRows += 1
		End If

		Return endOfRows
	End Function

	Private Function evaluateVertical(ByVal _x As Integer, ByVal _y As Integer) As Integer
		Dim value As Integer

		value += getVerticalEnd(currentColor, _x, _y) * ScoreConstants.notEndOfRow
		If value = 0
			Return value
		End If

		'// Evaluate Rows
		Select Case getVerticalCount(currentColor, _x, _y)
			Case >= 3
				value += ScoreConstants.win
			Case 2
				value += ScoreConstants.rowThree
			Case 1
				value += ScoreConstants.rowTwo
		End Select

		Return value
	End Function

	Private Function getVerticalCount(ByVal color As Integer, ByVal _x As Integer, ByVal _y As Integer) As Integer
		Dim count As Integer

		'// Evaluate Row
		For y As Integer = _y + 1 To _y + 3
			If ovalExists(_x, y) Then
				If Not ovals(_x, y) = 0 Then
					If ovals(_x, y) = color Then
						count += 1
						Continue For
					End If
				End If
			End If
			Exit For
		Next

		Return count
	End Function

	Private Function getVerticalEnd(ByVal color As Integer, ByVal _x As Integer, ByVal _y As Integer) As Integer
        If _y >= 3
           	Return 1
        End If
        Return 0
	End Function

	Private Function evaluateDiagonal(ByVal _x As Integer, ByVal _y As Integer) As Integer
		Dim value As Integer
		value += evaluateDiagonalTop(_x, _y)
		value += evaluateDiagonalBottom(_x, _y)
		Return value
	End Function

	Private Function evaluateDiagonalTop(ByVal _x As Integer, ByVal _y As Integer) As Integer
		Dim value As Integer

		value += getDiagonalTopEnd(currentColor, _x, _y) * ScoreConstants.notEndOfRow
		If value = 0
			Return value
		End If

		'// Evaluate Rows Top
		Select Case getDiagonalTopCount(currentColor, _x, _y)
			Case >= 3
				value += ScoreConstants.win
			Case 2
				value += ScoreConstants.rowThree
			Case 1
				value += ScoreConstants.rowTwo
		End Select

		Return value
	End Function

	Private Function getDiagonalTopCount(ByVal color As Integer, ByVal _x As Integer, ByVal _y As Integer) As Integer
		Dim index As Point
		Dim count As Integer

		'// Evaluate Top Start Right Rows
		index = New System.Drawing.Point(_x + 1, _y + 1)
		Do
			If ovalExists(index.X, index.Y) Then
				If Not ovals(index.X, index.Y) = 0 Then
					If ovals(index.X, index.Y) = color Then
						count += 1
						index.X += 1
						index.Y += 1
						If index.X = _x + 4 And index.Y = _y + 4 Then
							Exit Do
						End If
						Continue Do
					End If
				End If
			End If
			Exit Do
		Loop

		'// Evaluate Top Start Left Rows
		index = New System.Drawing.Point(_x - 1, _y - 1)
		Do
			If ovalExists(index.X, index.Y) Then
				If Not ovals(index.X, index.Y) = 0 Then
					If ovals(index.X, index.Y) = color Then
						count += 1
						index.X -= 1
						index.Y -= 1
						If index.X = _x - 4 And index.Y = _y - 4 Then
							Exit Do
						End If
						Continue Do
					End If
				End If
			End If
			Exit Do
		Loop

		Return count
	End Function

	Private Function getDiagonalTopEnd(ByVal color As Integer, ByVal _x As Integer, ByVal _y As Integer) As Integer
		Dim index As Point
		Dim topCount As Integer
		Dim bottomCount As Integer
		Dim endOfRows As Integer

		'// Evaluate Top Start Right Rows
		index = New System.Drawing.Point(_x + 1, _y + 1)
		Do
			If ovalExists(index.X, index.Y) Then
				If Not ovals(index.X, index.Y) = getOppositeColor() Then
					topCount += 1
					index.X += 1
					index.Y += 1
					If index.X = _x + 4 And index.Y = _y + 4 Then
						Exit Do
					End If
					Continue Do
				End If
			End If
			Exit Do
		Loop

		'// Evaluate Top Start Left Rows
		index = New System.Drawing.Point(_x - 1, _y - 1)
		Do
			If ovalExists(index.X, index.Y) Then
				If Not ovals(index.X, index.Y) = getOppositeColor() Then
					bottomCount += 1
					index.X -= 1
					index.Y -= 1
					If index.X = _x - 4 And index.Y = _y - 4 Then
						Exit Do
					End If
					Continue Do
				End If
			End If
			Exit Do
		Loop

		If topCount >= 3 Then
			endOfRows += 1
		End If

		If bottomCount >= 3 Then
			endOfRows += 1
		End If

		If topCount + bottomCount >= 3 And topCount < 3 And bottomCount < 3
			endOfRows += 1
		End If

		Return endOfRows
	End Function

	Private Function evaluateDiagonalBottom(ByVal _x As Integer, ByVal _y As Integer) As Integer
		Dim value As Integer

		value += getDiagonalBottomEnd(currentColor, _x, _y) * ScoreConstants.notEndOfRow
		If value = 0 Then
			Return value
		End If

		'// Evaluate Rows Bottom
		Select Case getDiagonalBottomCount(currentColor, _x, _y)
			Case >= 3
				value += ScoreConstants.win
			Case 2
				value += ScoreConstants.rowThree
			Case 1
				value += ScoreConstants.rowTwo
		End Select

		Return value
	End Function

	Private Function getDiagonalBottomCount(ByVal color As Integer, ByVal _x As Integer, ByVal _y As Integer) As Integer
		Dim index As Point
		Dim count As Integer

		'// Evaluate Bottom Start Left Rows
		index = New System.Drawing.Point(_x - 1, _y + 1)
		Do
			If ovalExists(index.X, index.Y) Then
				If Not ovals(index.X, index.Y) = 0 Then
					If ovals(index.X, index.Y) = color Then
						count += 1
						index.X -= 1
						index.Y += 1
						If index.X = _x - 4 And index.Y = _y + 4 Then
							Exit Do
						End If
						Continue Do
					End If
				End If
			End If
			Exit Do
		Loop

		'// Evaluate Bottom Start Right Rows
		index = New System.Drawing.Point(_x + 1, _y - 1)
		Do
			If ovalExists(index.X, index.Y) Then
				If Not ovals(index.X, index.Y) = 0 Then
					If ovals(index.X, index.Y) = color Then
						count += 1
						index.X += 1
						index.Y -= 1
						If index.X = _x + 4 And index.Y = _y - 4 Then
							Exit Do
						End If
						Continue Do
					End If
				End If
			End If
			Exit Do
		Loop

		Return count
	End Function

	Private Function getDiagonalBottomEnd(ByVal color As Integer, ByVal _x As Integer, ByVal _y As Integer) As Integer
		Dim index As Point
		Dim topCount As Integer
		Dim bottomCount As Integer
		Dim endOfRows As Integer

		'// Evaluate Bottom Start Left Rows
		index = New System.Drawing.Point(_x - 1, _y + 1)
		Do
			If ovalExists(index.X, index.Y) Then
				If Not ovals(index.X, index.Y) = getOppositeColor() Then
					topCount += 1
					index.X -= 1
					index.Y += 1
					If index.X = _x - 4 And index.Y = _y + 4 Then
						Exit Do
					End If
					Continue Do
				End If
			End If
			Exit Do
		Loop

		'// Evaluate Bottom Start Right Rows
		index = New System.Drawing.Point(_x + 1, _y - 1)
		Do
			If ovalExists(index.X, index.Y) Then
				If Not ovals(index.X, index.Y) = getOppositeColor() Then
					bottomCount += 1
					index.X += 1
					index.Y -= 1
					If index.X = _x + 4 And index.Y = _y - 4 Then
						Exit Do
					End If
					Continue Do
				End If
			End If
			Exit Do
		Loop

		If topCount >= 3 Then
			endOfRows += 1
		End If

		If bottomCount >= 3 Then
			endOfRows += 1
		End If

		If topCount + bottomCount >= 3 And topCount < 3 And bottomCount < 3
			endOfRows += 1
		End If

		Return endOfRows
	End Function

	Private Function isHorizontal(ByVal rowSize As Integer) As Boolean
		Dim count As Integer
		For y As Integer = 0 To gridSize.Y
			For x As Integer = 0 To gridSize.X
				If x = 0 Then
					count = 0
				End If
				count = checkSpot(x, y, count)
				If count = rowSize Then
					Return True
				End If
			Next
		Next
		Return False
	End Function

	Private Function isVertical(ByVal rowSize As Integer) As Boolean
		Dim count As Integer
		For x As Integer = 0 To gridSize.X
			For y As Integer = 0 To gridSize.Y
				If y = 0 Then
					count = 0
				End If
				count = checkSpot(x, y, count)
				If count = rowSize Then
					Return True
				End If
			Next
		Next
		Return False
	End Function

	Private Function isDiagonal(ByVal rowSize As Integer) As Boolean
		Dim count As Integer
		Dim index As Point

		For i As Integer = 0 To gridSize.X
			Do
				count = checkSpot(index.X + i, index.Y, count)
				If count = rowSize Then
					Return True
				End If

				index.X += 1
				index.Y += 1

				If index.X + i > gridSize.X Or index.Y > gridSize.Y Then
					index.X = 0
					index.Y = 0
					count = 0
					Exit Do
				End If
			Loop
		Next
		For i As Integer = 1 To gridSize.Y
			Do
				count = checkSpot(index.X, index.Y + i, count)
				If count = rowSize Then
					Return True
				End If

				index.X += 1
				index.Y += 1

				If index.X > gridSize.X Or index.Y + i > gridSize.Y Then
					index.X = 0
					index.Y = 0
					count = 0
					Exit Do
				End If
			Loop
		Next
		index.X = gridSize.X
		index.Y = 0
		For i As Integer = 0 To gridSize.X
			Do
				count = checkSpot(index.X - i, index.Y, count)
				If count = rowSize Then
					Return True
				End If

				index.X -= 1
				index.Y += 1

				If index.X - i < 0 Or index.Y > gridSize.Y Then
					index.X = gridSize.X
					index.Y = 0
					count = 0
					Exit Do
				End If
			Loop
		Next
		For i As Integer = 1 To gridSize.Y
			Do
				count = checkSpot(index.X, index.Y + i, count)
				If count = rowSize Then
					Return True
				End If

				index.X -= 1
				index.Y += 1

				If index.X < 0 Or index.Y + i > gridSize.Y Then
					index.X = gridSize.X
					index.Y = 0
					count = 0
					Exit Do
				End If
			Loop
		Next
		Return False
	End Function

	Private Function checkSpot(ByVal x As Integer, ByVal y As Integer, ByVal count As Integer) As Integer
		If ovals(x, y) = currentColor Then
			count += 1
		Else
			count = 0
		End If
		Return count
	End Function

	Private Function getWinType() As String
		If isHorizontal(_parent.connectSize) Then
			Return "horizontal"
		ElseIf isVertical(_parent.connectSize) Then
			Return "vertical"
		ElseIf isDiagonal(_parent.connectSize) Then
			Return "diagonal"
		End If
		Return ""
	End Function

	Private Function isWin() As Boolean
		If getWinType() = "" Then
			Return False
		End If
		Return True
	End Function

	Public Function checkWin() As Boolean
		Dim winType As String
		winType = getWinType()
		If Not winType = "" Then
			_parent.winMsg(winType)
			_parent.resetGrid()
			Return True
		ElseIf turnsLeft <= 0 Then
			MsgBox("Tie")
			_parent.resetGrid()
			Return True
		End If
		Return False
	End Function

	Private Function ovalExists(ByVal x As Integer, ByVal y As Integer) As Boolean
		If x > gridSize.X Or y > gridSize.Y Then
			Return False
		End If
		If 0 > x Or 0 > y Then
			Return False
		End If
		Return True
	End Function

	Private Sub placeOval(ByVal x As Integer, ByVal y As Integer)
		ovals(x, y) = currentColor
		placableLocations(x) -= 1
		turnsLeft -= 1
	End Sub
End Class

Public Class MyOval
	Inherits Microsoft.VisualBasic.PowerPacks.OvalShape

	Public arrayLocation As Point
	Private _parent As Object

	Public Sub New()
		SelectionColor = Color.Transparent
	End Sub

	Private Sub init(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles Me.ParentChanged
		If _parent Is Nothing Then
			_parent = Parent.Parent
		End If
	End Sub

	Public Sub fill()
		BackColor = _parent.currentColor
		setTag(_parent.colorToInt(_parent.currentColor))
		_parent.placableLocations(arrayLocation.X) -= 1
		_parent.turnsLeft -= 1
	End Sub

	Public Function isEmpty() As Boolean
		Return BackColor = SystemColors.Control
	End Function

	Public Sub setTag(ByVal value As Integer)
		Tag = value
	End Sub

	Public Function getTagColor() As Integer
		Return Tag
	End Function

	Public Function getColor() As Color
		Return BackColor
	End Function

	Public Function clone(ByVal parent As Object) As MyOval
		Dim newOval As MyOval
		newOval = New MyOval()

		With newOval
			.arrayLocation = New System.Drawing.Point(arrayLocation.X, arrayLocation.Y)
			.BackColor = BackColor
			.BackStyle = PowerPacks.BackStyle.Opaque
			.Location = New System.Drawing.Point(Location.X, Location.Y)
			.Parent = Parent
			._parent = .Parent.Parent
			.Size = New System.Drawing.Size(Size.Width, Size.Height)
			.Tag = Tag
		End With
		Return newOval
	End Function
End Class

Public Class ScoreConstants
	Public Const win As Integer = 100000000
	Public Const rowThree As Integer = 30000
	Public Const rowTwo As Integer = 200
	Public Const notEndOfRow As Integer = 20
End Class