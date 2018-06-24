<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>TicTacToe match</title>
        <link href="/css/main.css" type="text/css" rel="stylesheet">
    </head>
    <body>
        <h1>Match #${match.id} &laquo;${match.title}&raquo;</h1>
        <h2>Game &laquo;${match.game.title}&raquo;</h2>

        <h3>
            <c:choose>
                <c:when test="${match.status.finalized && match.status.id < 0}">
                    ${match.status.title}!
                </c:when>
                <c:when test="${match.status.finalized && match.status.id > 0}">
                    <b>&laquo;${match.moves.get(match.moves.size()-1).opponent}&raquo;</b> ${match.status.title}!
                </c:when>
                <c:otherwise>
                    Next move: <b>&laquo;${nextOpponent}&raquo;</b>
                </c:otherwise>
            </c:choose>
        </h3>

        <table border="1">
            <c:forEach begin="0" end="2" var="indexY">
                <tr>
                    <c:forEach begin="0" end="2" var="indexX">
                        <td>
                            <c:set var="cell" value="${board[indexX][indexY]}"/>
                            <c:choose>
                                <c:when test="${cell == null && !match.status.finalized}">
                                    <a href="/match/${match.id}/moveto/${indexX + indexY * 3}">--</a>
                                </c:when>
                                <c:when test="${cell == null && match.status.finalized}">
                                    &nbsp;&nbsp;&nbsp;
                                </c:when>
                                <c:otherwise>${cell}</c:otherwise>
                            </c:choose>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>

        <hr />
        <a href="/dashboard">Go Home</a>

    </body>
</html>