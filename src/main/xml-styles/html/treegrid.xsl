<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:rct="http://system.mp-objects.com/schemas/RTCalltreeReport"
	exclude-result-prefixes="rct #default">

	<xsl:output method="html" doctype-public="html" />

	<xsl:template match="/">
		<html>
			<head>
				<title>Runtime Call-Tree</title>
				<link href="style/jquery.treetable.css" rel="stylesheet" type="text/css" />
				<link href="style/style.css" rel="stylesheet" type="text/css" />
				<script src="style/jquery-1.11.1.min.js" type="text/javascript"></script>
				<script src="style/jquery.treetable.js" type="text/javascript"></script>
			</head>
			<body>
				<xsl:apply-templates select="//rct:calltree" />
				<hr />
				<footer>
					Generated by
					<a href="https://github.com/elmuerte/rtcalltree">RT Calltree</a>
				</footer>
				<script type="text/javascript">
					$(document).ready(function() {
					$("table.treegrid").treetable({ expandable: true, indent: 9 });
					});
				</script>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="rct:calltree">
		<section>
			<h1>
				<xsl:value-of select="@thread" />
			</h1>
			<table class="treegrid">
				<thead>
					<tr>
						<th>Depth</th>
						<th>Class</th>
						<th>Method</th>
						<th>Duration (ms)</th>
					</tr>
				</thead>
				<tbody>
					<xsl:apply-templates select=".//rct:entry" />
				</tbody>
			</table>
		</section>
	</xsl:template>

	<xsl:template match="rct:entry">
		<tr>
			<xsl:attribute name="data-tt-id">
				<xsl:value-of select="generate-id(.)" />
			</xsl:attribute>
			<xsl:if test="local-name(..) = 'entry'">
				<xsl:attribute name="data-tt-parent-id">
					<xsl:value-of select="generate-id(..)" />
				</xsl:attribute>
			</xsl:if>
			<td class="depth">
				<xsl:value-of select="@depth" />
			</td>
			<td class="klass">
				<xsl:value-of select="@class" />
			</td>
			<td class="method">
				<xsl:value-of select="@method" />
			</td>
			<td class="duration">
				<xsl:attribute name="title">
					<xsl:value-of select="format-number(@duration, '####,###,###,###')" />
					<xsl:text> ns</xsl:text>
				</xsl:attribute>
				<xsl:value-of select="format-number(@duration div 1000000, '####,###,###.###')" />
			</td>
		</tr>
	</xsl:template>

</xsl:stylesheet>
