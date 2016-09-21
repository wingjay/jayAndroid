#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include <unistd.h>
#include <ctype.h>
#include "license.h"

void genVersionFile(const void *buf, int len)
{
	FILE *ppFile = NULL;
        char snBuf[1024];
        snprintf(snBuf,1024,"Licensed by SoundAI Inc.\nPlease visit soundai.com \n\nLicensed to:%s\n",LICENSETO);

	if( access("/data", F_OK) == 0 )
	{
		//do not repeat writting file
		if( access(LICENSEAPATH, F_OK) )
		{
			ppFile = fopen(LICENSEAPATH, "wb");

			if(ppFile)
			{
				fwrite(snBuf, strlen(snBuf), 1, ppFile);
				fwrite(buf, len, 1, ppFile);
				fclose(ppFile);
			}
		}
	}

	if( access("/tmp", F_OK) == 0 )
	{
		//do not repeat writting file
		if( access(LICENSELPATH, F_OK) )
		{
			ppFile = fopen(LICENSELPATH, "wb");

			if(ppFile)
			{
				fwrite(snBuf, strlen(snBuf), 1, ppFile);
				fwrite(buf, len, 1, ppFile);
				fclose(ppFile);
			}
		}
	}

}

char* stristr(const char *str1,const char *str2)
{
	char *cp = (char*)str1;
	char *s1,*s2;

	if(!*str2)
		return((char *)str1);

	while(*cp)
	{
		s1 = cp;
		s2 = (char *)str2;

		while( *s1 && *s2 && !( tolower(*s1) - tolower(*s2) ) )
			s1++,s2++;

		if(!*s2)
			return(cp);

		cp++;
	}

	return(NULL);
}

int dateDiff(int y2,int m2,int d2)
{
	time_t st1;
	time(&st1);

	struct tm ptr2;
	memset(&ptr2,0,sizeof(struct tm));

	ptr2.tm_sec = 10;
	ptr2.tm_min = 10;
	ptr2.tm_hour = 10;
	ptr2.tm_mday = d2;
	ptr2.tm_mon = m2-1;
	ptr2.tm_year = y2-1900;
	time_t st2 = mktime(&ptr2);

	int rtn = (int)((st2-st1)/3600/24);
	//printf("date:%ld,%ld,%d\n",st2,st1,rtn);

	return rtn;
}

int getCPUinfo(char *cpu_arg,int len)
{
   FILE *fp_cpuinfo = NULL;
   fp_cpuinfo = fopen("/proc/cpuinfo", "rb");

   if(fp_cpuinfo == NULL) return 0;

   //int n_len = ftell(fp_cpuinfo);
   //fseek(fp_cpuinfo,0,SEEK_SET);

   /*size_t size = 0;
   while(getdelim(&cpu_arg, &size, 0, fp_cpuinfo) != -1)
   {
      puts(cpu_arg);
   }
   free(cpu_arg); */

   fread(cpu_arg,sizeof(char),len,fp_cpuinfo);

   fclose(fp_cpuinfo);

   return len;
}

int verifyLicenseCPU(char *compareName)
{
   char cpuinfo[2048];
   char snBuf[256];
   snprintf(snBuf,256,"Licensed cpuid:%s\nLicensed date:unlimited\n",compareName);

   genVersionFile(snBuf,strlen(snBuf));

   if( getCPUinfo(cpuinfo,2048) > 0 )
   {
	   if( stristr(cpuinfo,compareName) )
	   {
	       //printf("find:%s\n",compareName);
	       return 1;
	   }
   }

   //puts(cpuinfo);

   return 0;
}

int verifyLicenseDate(int nYear,int nMonth,int nDay)
{
    char snBuf[256];
    snprintf(snBuf,256,"Licensed date:%d-%d-%d\n",nYear,nMonth,nDay);

    genVersionFile(snBuf,strlen(snBuf));

    if( dateDiff(nYear,nMonth,nDay) >0 )
	return 1;

    return 0;

}


