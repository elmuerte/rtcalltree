<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:rct="http://system.mp-objects.com/schemas/RTCalltreeReport">

	<xsl:output method="text" />

	<xsl:template match="/">
		<xsl:text>calltree-id,depth,duration,class,method,start-time,thread-name,source-file,source-line&#10;</xsl:text>
		<xsl:apply-templates select="//rct:entry" />
	</xsl:template>

	<xsl:template match="rct:entry">
		<xsl:value-of select="generate-id(ancestor::rct:calltree)" />
		<xsl:text>,</xsl:text>
		<xsl:value-of select="@depth" />
		<xsl:text>,</xsl:text>
		<xsl:value-of select="@duration" />
		<xsl:text>,"</xsl:text>
		<xsl:value-of select="@class" />
		<xsl:text>","</xsl:text>
		<xsl:value-of select="@method" />
		<xsl:text>",</xsl:text>
		<xsl:value-of select="@start-time" />
		<xsl:text>,"</xsl:text>
		<xsl:value-of select="ancestor::rct:calltree/@thread" />
		<xsl:text>","</xsl:text>
		<xsl:value-of select="@source-file" />
		<xsl:text>",</xsl:text>
		<xsl:value-of select="@source-line" />
		<xsl:text>&#10;</xsl:text>
	</xsl:template>

</xsl:stylesheet>
