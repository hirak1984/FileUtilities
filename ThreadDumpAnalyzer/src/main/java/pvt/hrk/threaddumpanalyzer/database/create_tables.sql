USE [mixed_bag]
GO

/****** Object:  Table [dbo].[Dump_File]    Script Date: 12/16/2019 10:48:17 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Dump_File](
	[fileName] [nchar](50) NOT NULL,
	[dateTime] [datetime2](7) NULL,
	[description] [nchar](100) NULL,
	[linesIgnored] [nchar](255) NULL,
	[transactionid] [int] NOT NULL,
	[id] [int] NOT NULL,
 CONSTRAINT [PK_Dump_File] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
;
USE [mixed_bag]
GO

/****** Object:  Table [dbo].[Dump_Thread]    Script Date: 12/16/2019 10:48:59 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[Dump_Thread](
	[stackTrace] [varchar](max) NOT NULL,
	[threadName] [varchar](255) NULL,
	[state] [varchar](100) NULL,
	[daemon] [bit] NULL,
	[priority] [int] NULL,
	[tid] [bigint] NULL,
	[nid] [bigint] NULL,
	[message] [varchar](255) NULL,
	[identifier] [varchar](500) NULL,
	[id] [int] NOT NULL,
	[dumpfileid] [int] NOT NULL,
 CONSTRAINT [PK_Dump_Thread] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[Dump_Thread]  WITH CHECK ADD  CONSTRAINT [FK_DumpFile] FOREIGN KEY([dumpfileid])
REFERENCES [dbo].[Dump_File] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO

ALTER TABLE [dbo].[Dump_Thread] CHECK CONSTRAINT [FK_DumpFile]
GO
;

