package actionlistener;
import java.awt.event.*;
import biodata.Biodata;
import biodata.BiodataFrame;
import dao.BiodataDao;

public class HapusActionListener implements ActionListener{
   private final BiodataFrame biodataFrame; 
  private final BiodataDao biodataDao; 

  public HapusActionListener(BiodataFrame biodataFrame, BiodataDao biodataDao) {
    this.biodataFrame = biodataFrame;
    this.biodataDao = biodataDao;
  }
  public void actionPerformed(ActionEvent e) {
    int row = this.biodataFrame.getTable().getSelectedRow();
    int column = this.biodataFrame.getTable().getSelectedColumn();

    if (row == -1 || column == -1) {
      this.biodataFrame.showAlertFailed("dihapus");
      return;
    } else {
      String newValue = (String) this.biodataFrame.getTable().getModel().getValueAt(row, column);
      Biodata id = new Biodata();
      String col = "";
      
      switch (column) {
          case 0:
              col = "nama";
              break;
          case 1:
              col = "no_telepon";
              break;
          case 2:
              col = "jenis_kelamin";
              break;
          case 3:
              col = "alamat";
              break;
          default:
              // Tampilkan pesan kolom tidak ditemukan
              System.out.println("Kolom tidak ditemukan");
              break;
      }

	  // Set id dengan nilai id dari biodata yang akan dihapus
	  id = this.biodataDao.select(col, newValue);

      // Variable confirmation untuk menyimpan nilai dari message dialog konfirmasi
      int confirmation = this.biodataFrame.showConfirmation("hapus");

      // Jika confirmation bernilai 1
      if (confirmation == 1) {
        // Panggil method showAlertFailed pada biodataFrame dengan parameter "tidak dihapus"
        this.biodataFrame.showAlertFailed("tidak dihapus");
        // Batalkan proses
        return;
      } 
      // Jiak confirmation bernilai 0
      else {
        // Hapus tabel biodata berdasarkan id
        this.biodataFrame.deleteBiodata(id);
        // Hapus data biodata berdasarkan id
        this.biodataDao.delete(id);
        // Panggil method showAlertSuccess pada biodataFrame dengan parameter "dihapus"
        this.biodataFrame.showAlertSuccess("dihapus");
      }
    }
  }
    
}
