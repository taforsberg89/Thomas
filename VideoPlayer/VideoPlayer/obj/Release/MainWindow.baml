   M S B A M L   `   `   `  ����   VideoPlayer    VideoPlayer.MainWindow   \ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     -     �Name$  
mainWindow�� � Title$ 
MainWindow��$	��420��$	��600�� J� 	AllowDrop . .��� $��
0,0,-8,-31q� � ColumnDefinitions �� $
��279* ��� $	��71* ��� $	��51* ��� $
��199* �.+�r� -    r�Name$ mediaElement��$	��414��$	��600��$��Center=�$��Center=�$��
0,-10,0,16q� r� LoadedBehavior$ Manual=� r� UnloadedBehavior$ Manual=�$��4���� -    ��Name$ pauseButton��$��Pause��$
��Left=�$��
10,314,0,0q�$	��Top=�|�.  $��44�� J� Opacity$	 0.5Q��� -   $ 
playButton��$��New Episode��$
��Left=�$��177,314,0,0q�$	��Top=�|�.  $	 0.5Q��� -   	 ��Name$	 
seekSlider��$
��Left=�$��
10,291,0,0q�$	��Top=�$	��568��$	 0.5Q�$��4���� -   $	 volumeSlider��$
��Left=�$��
22,314,0,0q�$	��Top=�
 J� RenderTransformOrigin$
 
0.74,0.111&� � Maximum$ 1Q� � LargeChange$	 0.1Q� � SmallChange$
 0.01Q� � Value$ 1Q�$	��135��$	 0.5Q�$��3���� -   $ loadFilesButton��$��Set Directory��$
��Left=�$��250,314,0,0q�$	��Top=�|�.  $��76��$	 0.5Q�$��2���� -    ��Name$ 	statusBar��$
��Left=�$��
10,339,0,0q�$
��Wrap=�$	��Top=�$	��580��$
��Bold"�
�������$��4���� -	    ��Name$ onTop��$��CheckBox��$
��Left=�$��149,317,0,0q�$	��Top=�$��21�� � ToolTip$ Always On Top��$	 0.5Q��� -
   $ filterButton��$��2��$��
Add Filter��$��1��$
��Left=�$��
47,314,0,0q�$	��Top=�$��75��$
 0.307,0.593&�$	 0.5Q�