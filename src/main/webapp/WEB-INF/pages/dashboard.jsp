<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <title>TicTacToe dashboard</title>
        <link href="/css/main.css" type="text/css" rel="stylesheet">
    </head>
    <body>
        <h1>Create new match</h1>

        <form:form method="post" action="/match/create" modelAttribute="matchRequest">
            <form:label path="title">Match name</form:label>
            <form:input path="title" />
            <br />
            <form:label path="gameId">Choose game: </form:label>
            <c:forEach items="${games}" var="game">
                <form:radiobutton
                        path="gameId"
                        value="${game.id}"

                        disabled="${!game.active}" />
                    <span class="title">${game.title}</span>
            </c:forEach>
            <br />
            <input type="submit" value="Let the fight begin!" />
        </form:form>


        <c:if test="${matchesInProgress.size() == 0}">
            <h1>No matches in progress</h1>
        </c:if>
        <c:if test="${matchesInProgress.size() > 0}">

            <h1>Matches in progress</h1>

            <table border="1">
                <thead>
                    <tr>
                        <th>Match title</th>
                        <th>Game title</th>
                        <th>Started</th>
                        <th>Last move</th>
                        <th>&nbsp;</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${matchesInProgress}" var="matchInProgress">
                        <tr>
                            <td>&laquo;${matchInProgress.title}&raquo;</td>
                            <td>&laquo;${matchInProgress.game.title}&raquo;</td>
                            <c:choose>
                                <c:when test="${matchInProgress.lastMove != null}">
                                    <td>${matchInProgress.firstMove.opponent}@${matchInProgress.firstMove.performed}</td>
                                    <td>${matchInProgress.lastMove.opponent}@${matchInProgress.lastMove.performed}</td>
                                    <td><a href="/match/${matchInProgress.id}/show">Resume match</a></td>
                                </c:when>
                                <c:otherwise>
                                    <td colspan="2">&nbsp;</td>
                                    <td><a href="/match/${matchInProgress.id}/show">Start match!</a></td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </c:if>

        <h1>Matches History</h1>

        <table border="1">
            <thead>
            <tr>
                <th>Match title</th>
                <th>Game title</th>
                <th>Started</th>
                <th>Last move</th>
                <th>Result</th>
                <th>&nbsp;</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${finishedMatches}" var="finishedMatch">
                <tr>
                    <td>&laquo;${finishedMatch.title}&raquo;</td>
                    <td>&laquo;${finishedMatch.game.title}&raquo;</td>
                    <td>${finishedMatch.firstMove.opponent}@${finishedMatch.firstMove.performed}</td>
                    <td>${finishedMatch.lastMove.opponent}@${finishedMatch.lastMove.performed}</td>
                    <c:choose>
                        <c:when test="${finishedMatch.status.id < 0}">
                            <td>${finishedMatch.status.title}</td>
                        </c:when>
                        <c:otherwise>
                            <td>
                                &laquo;${finishedMatch.lastMove.opponent}&raquo;
                                ${finishedMatch.status.title} at ${finishedMatch.moves.size()} move
                            </td>
                        </c:otherwise>
                    </c:choose>
                    <td><a href="/match/${finishedMatch.id}/show">Show board</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </body>
</html>