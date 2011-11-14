package wii.edu.core.dao;
import java.util.List;
import wii.edu.core.model.Mahasiswa;
import wii.edu.core.model.RegistrasiMatakuliah;
import wii.edu.core.model.Semester;

public class MahasiswaIP {

    public MahasiswaIP() {
    }
    
    public double getIPK(Mahasiswa mahasiswa) throws Exception {
        double IPK=0;
        Semester semester=new SemesterDAO().getCurrentSemester();
        List<RegistrasiMatakuliah> matakuliahlist=new RegistrasiMatakuliahDAO().getAllMatakuliahIPK(mahasiswa,semester);
        double BobotSKS;
        double BobotNilai=0;
        double oldBobotNilai=0;
        double KBN=0;
        double TotalSKS=0;
        if(matakuliahlist.size()>0) {
            for(int i=0;i<matakuliahlist.size();i++) {
                BobotSKS=matakuliahlist.get(i).getMatakuliah().getSksAkademik();
                BobotNilai=0;
                if(matakuliahlist.get(i).getNilai()!=null) {
                    if(matakuliahlist.get(i).getNilai().equals("A")) {
                        BobotNilai=4;
                    } else if(matakuliahlist.get(i).getNilai().equals("B")) {
                        BobotNilai=3;
                    } else if(matakuliahlist.get(i).getNilai().equals("C")) {
                        BobotNilai=2;
                    } else if(matakuliahlist.get(i).getNilai().equals("D")) {
                        BobotNilai=1;
                    }
                    System.out.println(i+":i:"+matakuliahlist.get(i).getMatakuliah().getNama()+":"+BobotNilai);
                    for(int j=i-1;j>=0;j--) {
                        if(j<0) {
                            break;
                        }
                        if(matakuliahlist.get(i).getMatakuliah().getId()==matakuliahlist.get(j).getMatakuliah().getId()) {
                            oldBobotNilai=0;
                            if(matakuliahlist.get(j).getNilai()!=null) {
                                if(matakuliahlist.get(j).getNilai().equals("A")) {
                                    oldBobotNilai=4;
                                } else if(matakuliahlist.get(j).getNilai().equals("B")) {
                                    oldBobotNilai=3;
                                } else if(matakuliahlist.get(j).getNilai().equals("C")) {
                                    oldBobotNilai=2;
                                } else if(matakuliahlist.get(j).getNilai().equals("D")) {
                                    oldBobotNilai=1;
                                }
                            }
                            System.out.println(j+":j:"+matakuliahlist.get(j).getMatakuliah().getNama()+":"+oldBobotNilai);
                            if(oldBobotNilai>=BobotNilai) {
                                System.out.println("---------------a");
                                BobotNilai=0;
                                BobotSKS=0;
                            } else {
                                System.out.println("---------------a");
                                KBN-=BobotSKS*oldBobotNilai;
                                KBN+=BobotSKS*BobotNilai;
                                BobotSKS=0;
                            }
                            break;
                        }
                    }
                }
                System.out.println("BobotSKS"+BobotSKS);
                KBN+=BobotSKS*BobotNilai;
                TotalSKS+=BobotSKS;
            }
            System.out.println("KBN"+KBN);
            System.out.println("TotalSKS"+TotalSKS);
            System.out.println("IPK"+IPK);
            IPK=(double)KBN/TotalSKS;
        }
        return IPK;
    }
    public double getIPS(Mahasiswa mahasiswa,Semester semester) throws Exception {
        double IPS=0;
        List<RegistrasiMatakuliah> matakuliahlist=new RegistrasiMatakuliahDAO().getMatakuliahMahasiswa(mahasiswa,semester);
        int BobotSKS;
        int BobotNilai=0;
        int KBN=0;
        int TotalSKS=0;
        if(matakuliahlist.size()>0) {
            for(int i=0;i<matakuliahlist.size();i++) {
                BobotSKS=matakuliahlist.get(i).getMatakuliah().getSksAkademik();
                if(matakuliahlist.get(i).getNilai()!=null) {
                    if(matakuliahlist.get(i).getNilai().equals("A")) {
                        BobotNilai=4;
                    } else if(matakuliahlist.get(i).getNilai().equals("B")) {
                        BobotNilai=3;
                    } else if(matakuliahlist.get(i).getNilai().equals("C")) {
                        BobotNilai=2;
                    } else if(matakuliahlist.get(i).getNilai().equals("D")) {
                        BobotNilai=1;
                    }
                }
                KBN+=BobotSKS*BobotNilai;
                TotalSKS+=BobotSKS;
            }
            IPS=(double)KBN/TotalSKS;
        }
        System.out.println("IPS"+IPS);
        return IPS;
    }
    public int getMaxBeban(double IPK,double IPS) {
        int MaxBeban=0;
        int MaxSKS[]={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,20,21,21,22,22,23,23,24,24,25,25,26,26,27,27,28,28,29,30};
        double d=IPK;
        if(d>IPS)d=IPS;
        d*=10;
        if((int)d-1>0)MaxBeban=MaxSKS[(int)d-1];
        if(MaxBeban<18)MaxBeban=18;
        return MaxBeban;
    }
}