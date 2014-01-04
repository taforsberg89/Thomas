using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Diagnostics;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Forms;
using System.IO;
using System.IO.IsolatedStorage;
using System.Windows.Threading;


namespace VideoPlayer
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
  
    /// </summary>
    public partial class MainWindow : Window
    {
        DispatcherTimer timer;
        bool fullscreen = false;
        string sourceFile, nameOfCurrentShow;
        bool paused = false;
        bool playing = false;
        
        public MainWindow()
        {
            InitializeComponent();
          
            timer = new DispatcherTimer();
            timer.Interval = TimeSpan.FromMilliseconds(500);
            timer.Tick += timer_Tick;
        }

        private void hideUI()
        {
            seekSlider.Visibility = System.Windows.Visibility.Hidden;
            volumeSlider.Visibility = System.Windows.Visibility.Hidden;
            playButton.Visibility = System.Windows.Visibility.Hidden;
            pauseButton.Visibility = System.Windows.Visibility.Hidden;
            statusBar.Visibility = System.Windows.Visibility.Hidden;
            onTop.Visibility = System.Windows.Visibility.Hidden;
           
            loadFilesButton.Visibility = System.Windows.Visibility.Hidden;
        }

        private void showUI()
        {
            seekSlider.Visibility = System.Windows.Visibility.Visible;
            volumeSlider.Visibility = System.Windows.Visibility.Visible;
            playButton.Visibility = System.Windows.Visibility.Visible;
            pauseButton.Visibility = System.Windows.Visibility.Visible;
            onTop.Visibility = System.Windows.Visibility.Visible;
           
            statusBar.Visibility = System.Windows.Visibility.Visible;
            loadFilesButton.Visibility = System.Windows.Visibility.Visible;
        }

        void timer_Tick(object sender, EventArgs e)
        {
            seekSlider.Value = mediaElement.Position.TotalSeconds;
        }

        private void playButton_Click(object sender, RoutedEventArgs e)
        {
            statusBar.Foreground = Brushes.White;
            try
            {
                sourceFile = getrandomfile(Properties.Settings.Default["Path"].ToString());
                nameOfCurrentShow = sourceFile.Substring(Properties.Settings.Default["Path"].ToString().Length + 1);
                statusBar.Text = "Playing: " + nameOfCurrentShow;
                mediaElement.Source = new Uri(sourceFile);
                if (mediaElement.IsLoaded)
                {
                    mediaElement.Volume = 1;
                    mediaElement.Play();
                    playing = true;
                }
            }
            catch (Exception ex)
            {
                
                if (!mediaElement.IsLoaded)
                {
                    statusBar.Foreground = Brushes.Black;
                 
                }

                statusBar.Text = "Directory contains no .avi or .mp4 files";
            }
            
        }

        private void pauseButton_Click(object sender, RoutedEventArgs e)
        {
            statusBar.Foreground = Brushes.White;
            if (paused)
            {
                pauseButton.Content = "Pause";
                mediaElement.Play();
                paused = false;
                statusBar.Text = "Playing: " + nameOfCurrentShow;
            }
            else
            {
                pauseButton.Content = "Play";
                mediaElement.Pause();
                statusBar.Text = "Paused: " + nameOfCurrentShow;
                paused = true;
            }
           
        }

        private void PlayPauseToggle()
        {
            if (paused)
            {
                pauseButton.Content = "Pause";
                mediaElement.Play();
                paused = false;
                statusBar.Text = "Playing: " + nameOfCurrentShow;
            }
            else
            {
                pauseButton.Content = "Play";
                mediaElement.Pause();
                paused = true;
                statusBar.Text = "Paused: " + nameOfCurrentShow;
            }
        }

       

        private void volumeSlider_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            mediaElement.Volume = (double)volumeSlider.Value;
        }

        private void seekSlider_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            mediaElement.Position = TimeSpan.FromSeconds(seekSlider.Value);
        }

       

        private void mediaElement_MediaOpened(object sender, RoutedEventArgs e)
        {
            TimeSpan ts = mediaElement.NaturalDuration.TimeSpan;
            seekSlider.Maximum = ts.TotalSeconds;
            timer.Start();
        }

        private void mediaElement_MouseDown(object sender, MouseButtonEventArgs e)
        {
            
            if (e.ClickCount == 2 && fullscreen == false)
            {

                this.WindowStyle = WindowStyle.None;
                this.WindowState = WindowState.Maximized;
                hideUI();
                fullscreen = true;
                mediaElement.Width = mainWindow.RenderSize.Width;
                mediaElement.Height = mainWindow.RenderSize.Height;
                System.Windows.Forms.Cursor.Hide();
            }
            else if (e.ClickCount == 2 && fullscreen == true)
            {

                this.WindowStyle = WindowStyle.SingleBorderWindow;
                this.WindowState = WindowState.Normal;
                showUI();
                fullscreen = false;
                
                System.Windows.Forms.Cursor.Show();
                mediaElement.Width = mainWindow.RenderSize.Width;
                mediaElement.Height = mainWindow.RenderSize.Height;
              
            }

           
        }

        private void loadFiles_Click(object sender, RoutedEventArgs e)
        {
            FolderBrowserDialog browser = new FolderBrowserDialog();
            browser.Description = "Select source directory\nSub directories are included";
            browser.ShowNewFolderButton = false;
            browser.RootFolder = System.Environment.SpecialFolder.MyComputer;
            browser.ShowDialog();
            Properties.Settings.Default["Path"] = browser.SelectedPath;
            Properties.Settings.Default.Save();
            
        }

        private string getrandomfile(string path)
        {
            string file = null;
            if (!string.IsNullOrEmpty(path))
            {
                var extensions = new string[] { ".avi", ".mp4", ".mkv" };
                try
                {
                    var di = new DirectoryInfo(path);
                    var rgFiles = di.GetFiles("*.*", SearchOption.AllDirectories).Where(f => extensions.Contains(f.Extension.ToLower()));
                    int fileCount = rgFiles.Count();
                    if (fileCount > 0)
                    {
                        int x = new Random().Next( 0, fileCount );
                        file = rgFiles.ElementAt(x).FullName;
                    }
                }
                
                catch {}
            }
            return file;
        }

        private void mainWindow_KeyDown(object sender, System.Windows.Input.KeyEventArgs e)
        {
            if (e.Key == Key.Space)
            {
                PlayPauseToggle();
                
            }
            if (e.Key == Key.Escape)
            {
                if (fullscreen)
                {
                    this.WindowStyle = WindowStyle.SingleBorderWindow;
                    this.WindowState = WindowState.Normal;
                    showUI();
                    fullscreen = false;
                    System.Windows.Forms.Cursor.Show();
                    mediaElement.Width = mainWindow.RenderSize.Width;
                    mediaElement.Height = mainWindow.RenderSize.Height;

                }
            }
        }

        private void mainWindow_SizeChanged(object sender, SizeChangedEventArgs e)
        {
            mediaElement.Width = mainWindow.RenderSize.Width;
            mediaElement.Height = mainWindow.RenderSize.Height;
        }

        

       

        private void onTop_Unchecked(object sender, RoutedEventArgs e)
        {
            mainWindow.Topmost = false;
        }

        private void onTop_Checked(object sender, RoutedEventArgs e)
        {
            mainWindow.Topmost = true;
        }

      

       
             
        

        private void mainWindow_MouseLeave(object sender, System.Windows.Input.MouseEventArgs e)
        {
            hideUI();
        }

        private void mainWindow_MouseEnter(object sender, System.Windows.Input.MouseEventArgs e)
        {
      
            showUI();
        }
    }
}
