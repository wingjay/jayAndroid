#ifndef SAI_LICENSE_H
#define SAI_LICENSE_H

#define LICENSEAPATH "/data/sai_license.dat"
#define LICENSELPATH "/tmp/sai_license.dat"
#define LICENSETO "QIHU360"


#if defined(__cplusplus)
extern "C"
{
#endif

	int verifyLicenseCPU(char *compareName);   // 1 for normal
    int verifyLicenseDate(int nYear,int nMonth,int nDay); // 1 for normal


#if defined(__cplusplus)
}
#endif


#endif
