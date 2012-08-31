<br>

<irSysPar:getPar name="gemeente" var="par"/>

<center>
  <g:if test="${par?.value.equals('Rotterdam')}">
    <img class="logo" src="${resource(dir:'images/kcc',file:'logo_rotterdam.jpg')}" alt="Gemeente Rotterdam"/>
  </g:if>
  <g:elseif test="${par?.value.equals('BER')}">
    <img class="logo" src="${resource(dir:'images/kcc',file:'logo_boz.jpg')}" alt="Gemeente Bergen op Zoom"/>
    <img class="logo" src="${resource(dir:'images/kcc',file:'logo_ettenleur.gif')}" alt="Gemeente Etten-Leur"/>
    <img class="logo" src="${resource(dir:'images/kcc',file:'logo_roosendaal.jpg')}" alt="Gemeente Roosendaal"/>
  </g:elseif>
  <g:elseif test="${par?.value.equals('Etten-Leur')}">
    <img class="logo" src="${resource(dir:'images/kcc',file:'logo_ettenleur.gif')}" alt="Gemeente Etten-Leur"/>
  </g:elseif>
  <g:else>
    <img class="logo" src="${resource(dir:'images/kcc',file:'logo.png')}" alt="Publiek.cc"/>
  </g:else>
</center>

<div style="height: 60px"></div>
<g:render  template="/layouts/IRMenu" model="[menuXPos:'15px',menuYPos:'120px']"/>
