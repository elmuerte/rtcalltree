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

					function expandAll(tableId) {
					$('#'+tableId).treetable('expandAll');
					}

					function collapseAll(tableId) {
					$('#'+tableId).treetable('collapseAll');
					}
				</script>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="rct:calltree">
		<xsl:variable name="tableid" select="generate-id(.)" />
		<section>
			<h1>
				<xsl:value-of select="@thread" />
			</h1>
			<p>
				<xsl:value-of select="count(.//rct:entry)" />
				<xsl:text> entries.</xsl:text>
			</p>
			<p>
				<a href="#" onclick="expandAll('{$tableid}'); return false;">expand all</a>
				<xsl:text> </xsl:text>
				<a href="#" onclick="collapseAll('{$tableid}'); return false;">collapse all</a>
			</p>
			<table class="treegrid" id="{$tableid}">
				<thead>
					<tr>
						<th>Depth</th>
						<th>Class</th>
						<th>Method</th>
						<th>Duration (ms)</th>
						<th>Pct (rel)</th>
						<th>Pct (abs)</th>
					</tr>
				</thead>
				<tbody>
					<xsl:apply-templates select=".//rct:entry" />
				</tbody>
			</table>
		</section>
	</xsl:template>

	<xsl:template match="rct:entry">
		<xsl:variable name="prev" select="preceding-sibling::rct:entry[1]" />
		<xsl:variable name="isSame"
			select="$prev/@depth = @depth and $prev/@class = @class and $prev/@method = @method and count($prev/rct:parameter) = count(rct:parameter) and count(rct:entry) = 0" />

		<xsl:if test="not($isSame)">
			<xsl:variable name="next" select="following-sibling::rct:entry[1]" />
			<xsl:variable name="isNextSame"
				select="$next/@depth = @depth and $next/@class = @class and $next/@method = @method and count($next/rct:parameter) = count(rct:parameter) and count(rct:entry) = 0" />

			<tr>
				<xsl:attribute name="data-tt-id">
					<xsl:value-of select="generate-id(.)" />
				</xsl:attribute>
				<xsl:if test="local-name(..) = 'entry'">
					<xsl:attribute name="data-tt-parent-id">
					<xsl:value-of select="generate-id(..)" />
				</xsl:attribute>
				</xsl:if>
				<xsl:attribute name="class">
					<xsl:text>depth</xsl:text>
					<xsl:value-of select="@depth" />
				</xsl:attribute>
				<td class="depth">
					<xsl:value-of select="@depth" />
					<xsl:if test="$isNextSame">
						<strong class="repeating">
							<xsl:text> +</xsl:text>
						</strong>
					</xsl:if>
				</td>
				<td class="klass">
					<xsl:value-of select="@class" />
				</td>
				<td class="method">
					<xsl:value-of select="@method" />
				</td>
				<td class="duration">
					<xsl:attribute name="title">
						<xsl:value-of select="format-number(@duration, '####,###,##0,000')" />
						<xsl:text> ns</xsl:text>
					</xsl:attribute>
					<xsl:value-of select="format-number(@duration div 1000000, '####,###,##0.000')" />
				</td>
				<td class="pct">
					<xsl:value-of select="format-number(@duration div ../@duration * 100, '0.00' )" />
					<xsl:text>%</xsl:text>
				</td>
				<td class="pctAbsolute">
					<xsl:value-of select="format-number(@duration div ancestor::rct:calltree/rct:entry[1]/@duration * 100, '0.00' )" />
					<xsl:text>%</xsl:text>
				</td>
			</tr>

			<xsl:if test="$isNextSame">
				<xsl:apply-templates select="." mode="collapse" />
			</xsl:if>
		</xsl:if>
	</xsl:template>

	<xsl:template match="rct:entry" mode="collapse">
		<xsl:variable name="curr" select="." />
		<!-- first element which is not the "same" -->
		<xsl:variable name="differentElm"
			select="following-sibling::rct:entry[not($curr/@depth = @depth and $curr/@class = @class and $curr/@method = @method and count($curr/rct:parameter) = count(rct:parameter) and count(rct:entry) = 0)][1]" />

		<!-- find the position of this element -->
		<xsl:variable name="diffPos">
			<xsl:choose>
				<xsl:when test="$differentElm">
					<xsl:value-of select="count($differentElm/preceding-sibling::rct:entry) - count($curr/preceding-sibling::rct:entry)" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="count(following-sibling::rct:entry) + 1" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<xsl:variable name="repeaters" select="following-sibling::rct:entry[position() &lt; $diffPos]" />

		<xsl:variable name="totalDuration" select="sum($repeaters/@duration)" />

		<tr>
			<xsl:attribute name="data-tt-id">
				<xsl:value-of select="concat(generate-id(.), '__c')" />
			</xsl:attribute>
			<xsl:attribute name="data-tt-parent-id">
				<xsl:value-of select="generate-id(..)" />
			</xsl:attribute>
			<xsl:attribute name="class">
				<xsl:text>collapse depth</xsl:text>
				<xsl:value-of select="@depth" />
			</xsl:attribute>
			<td class="depth">
			</td>
			<td class="klass">
				<xsl:text>Repeated </xsl:text>
				<xsl:value-of select="count($repeaters)" />
				<xsl:text> more time(s)</xsl:text>
			</td>
			<td class="method">
			</td>
			<td class="duration">
				<xsl:attribute name="title">
						<xsl:value-of select="format-number($totalDuration, '####,###,##0,000')" />
						<xsl:text> ns</xsl:text>
					</xsl:attribute>
				<xsl:value-of select="format-number($totalDuration div 1000000, '####,###,##0.000')" />
			</td>
			<td class="pct">
				<xsl:value-of select="format-number($totalDuration div ../@duration * 100, '0.00' )" />
				<xsl:text>%</xsl:text>
			</td>
			<td class="pctAbsolute">
				<xsl:value-of select="format-number($totalDuration div ancestor::rct:calltree/rct:entry[1]/@duration * 100, '0.00' )" />
				<xsl:text>%</xsl:text>
			</td>
		</tr>
	</xsl:template>

</xsl:stylesheet>
